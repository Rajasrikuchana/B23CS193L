package com.example.shorten_URL.controller;

import com.example.shorten_URL.dto.ShortenRequest;
import com.example.shorten_URL.dto.ShortenResponse;
import com.example.shorten_URL.dto.StatsResponse;
import com.example.shorten_URL.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorturls")
public class ShortenController {

    private final UrlService urlService;

    public ShortenController(UrlService urlService) {
        this.urlService = urlService;
    }

    // -----------------------------
    // 1️⃣ Create Short URL
    // POST /shorturls
    // -----------------------------
    @PostMapping
    public ResponseEntity<ShortenResponse> createShortUrl(@RequestBody ShortenRequest request) {
        // Validate input
        if (request.getUrl() == null || request.getUrl().isBlank()) {
            return ResponseEntity.badRequest().body(null);
        }

        ShortenResponse response = urlService.createShortUrl(request);
        return ResponseEntity.ok(response);
    }

    // -----------------------------
    // Get URL Statistics
    // GET /shorturls/{shortcode}
    // -----------------------------
    @GetMapping("/{shortcode}")
    public ResponseEntity<StatsResponse> getStats(@PathVariable String shortcode) {
        StatsResponse stats = urlService.getStats(shortcode);
        return ResponseEntity.ok(stats);
    }
}
