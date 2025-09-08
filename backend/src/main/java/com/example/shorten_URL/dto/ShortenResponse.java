package com.example.shorten_URL.dto;

import java.time.LocalDateTime;

public class ShortenResponse {
    private String shortLink;
    private LocalDateTime expiry;

    public ShortenResponse(String shortLink, LocalDateTime expiry) {
        this.shortLink = shortLink;
        this.expiry = expiry;
    }

    public String getShortLink() { return shortLink; }
    public LocalDateTime getExpiry() { return expiry; }
}
