package com.example.shorten_URL.model;

import java.time.LocalDateTime;

public class ClickStats {
    private LocalDateTime timestamp;
    private String referrer;
    private String location; // simple mock

    public ClickStats(LocalDateTime timestamp, String referrer, String location) {
        this.timestamp = timestamp;
        this.referrer = referrer;
        this.location = location;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getReferrer() {
        return referrer;
    }

    public String getLocation() {
        return location;
    }
}
