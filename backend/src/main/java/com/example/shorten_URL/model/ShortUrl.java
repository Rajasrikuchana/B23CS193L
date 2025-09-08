package com.example.shorten_URL.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShortUrl {
    private String originalUrl;
    private String shortCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private List<ClickStats> clicks = new ArrayList<>(); // ✅ added

    public ShortUrl(String originalUrl, String shortCode, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public String getOriginalUrl() { return originalUrl; }
    public String getShortCode() { return shortCode; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public boolean isExpired() { return LocalDateTime.now().isAfter(expiresAt); }

    // ✅ Click handling
    public List<ClickStats> getClicks() { return clicks; }
    public void addClick(ClickStats click) { clicks.add(click); }
}
