package com.shortenme.urlShortener.service;

import com.shortenme.urlShortener.entity.UrlMapping;
import org.springframework.stereotype.Service;

@Service
public interface UrlMappingService {
    public UrlMapping findById(long id);

    public String insertAndReturnShort(String longUrl);

    public String findOriginalUrlByShortKey(String shortKey);
}
