package com.example.shorten_URL.service;

import com.example.shorten_URL.dto.ShortenRequest;
import com.example.shorten_URL.dto.ShortenResponse;
import com.example.shorten_URL.dto.StatsResponse;
import com.example.shorten_URL.exception.ConflictException;
import com.example.shorten_URL.exception.ExpiredLinkException;
import com.example.shorten_URL.exception.NotFoundException;
import com.example.shorten_URL.model.ClickStats;
import com.example.shorten_URL.model.ShortUrl;
import com.example.shorten_URL.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UrlService {

    private final ShortUrlRepository repository;

    public UrlService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    // -----------------------------
    // 1️⃣ Create Short URL
    // -----------------------------
    public ShortenResponse createShortUrl(ShortenRequest request) {
        if (request.getUrl() == null || request.getUrl().isBlank()) {
            throw new IllegalArgumentException("URL is required");
        }

        String shortcode;

        // Use custom shortcode if provided
        if (request.getShortcode() != null && !request.getShortcode().isBlank()) {
            if (repository.existsByCode(request.getShortcode())) {
                throw new ConflictException("Shortcode already exists: " + request.getShortcode());
            }
            shortcode = request.getShortcode();
        } else {
            shortcode = generateCode();
            while (repository.existsByCode(shortcode)) {
                shortcode = generateCode();
            }
        }

        int validity = (request.getValidity() != null) ? request.getValidity() : 30; // default 30 mins
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusMinutes(validity);

        ShortUrl shortUrl = new ShortUrl(request.getUrl(), shortcode, createdAt, expiresAt);
        repository.save(shortUrl);

        String shortLink = "http://localhost:8080/" + shortcode;
        return new ShortenResponse(shortLink, expiresAt);
    }

    // -----------------------------
    // 2️⃣ Redirect & Click Tracking
    // -----------------------------
    public String getOriginalUrl(String code, String referrer, String location) {
        ShortUrl shortUrl = repository.findByCode(code);
        if (shortUrl == null) {
            throw new NotFoundException("Shortcode not found: " + code);
        }
        if (shortUrl.isExpired()) {
            throw new ExpiredLinkException("Short link expired: " + code);
        }

        // Record click
        ClickStats click = new ClickStats(LocalDateTime.now(), referrer != null ? referrer : "direct", location != null ? location : "Unknown");
        shortUrl.addClick(click);

        return shortUrl.getOriginalUrl();
    }

    // -----------------------------
    // 3️⃣ Retrieve Stats
    // -----------------------------
    public StatsResponse getStats(String code) {
        ShortUrl shortUrl = repository.findByCode(code);
        if (shortUrl == null) {
            throw new NotFoundException("Shortcode not found: " + code);
        }

        return new StatsResponse(
                shortUrl.getShortCode(),
                shortUrl.getOriginalUrl(),
                shortUrl.getCreatedAt(),
                shortUrl.getExpiresAt(),
                shortUrl.getClicks().size(),
                shortUrl.getClicks()
        );
    }

    // -----------------------------
    //  Generate Random Code
    // -----------------------------
    private String generateCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
