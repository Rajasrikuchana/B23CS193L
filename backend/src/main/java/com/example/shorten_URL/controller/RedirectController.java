package com.example.shorten_URL.controller;

import com.example.shorten_URL.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedirectController {
    private final UrlService urlService;

    public RedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortcode}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortcode,
            @RequestHeader(value = "Referer", required = false) String referrer
    ) {
        if (referrer == null) {
            referrer = "direct";
        }
        String location = "Unknown"; // mock location

        String originalUrl = urlService.getOriginalUrl(shortcode, referrer, location);

        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, originalUrl)
                .build();
    }
}
