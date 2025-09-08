package com.example.shorten_URL.dto;

import com.example.shorten_URL.model.ClickStats;

import java.time.LocalDateTime;
import java.util.List;

public class StatsResponse {
    private String shortCode;
    private String originalUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private int totalClicks;
    private List<ClickStats> clickDetails;

    public StatsResponse(String shortCode, String originalUrl,
                         LocalDateTime createdAt, LocalDateTime expiresAt,
                         int totalClicks, List<ClickStats> clickDetails) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.totalClicks = totalClicks;
        this.clickDetails = clickDetails;
    }

    public String getShortCode() { return shortCode; }
    public String getOriginalUrl() { return originalUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public int getTotalClicks() { return totalClicks; }
    public List<ClickStats> getClickDetails() { return clickDetails; }
}
