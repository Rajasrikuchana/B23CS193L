package com.example.shorten_URL.repository;

import com.example.shorten_URL.model.ShortUrl;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ShortUrlRepository {
    private final Map<String, ShortUrl> store = new ConcurrentHashMap<>();

    public void save(ShortUrl shortUrl) {
        store.put(shortUrl.getShortCode(), shortUrl);
    }

    public ShortUrl findByCode(String code) {
        return store.get(code);
    }

    public boolean existsByCode(String code) {
        return store.containsKey(code);
    }
}
