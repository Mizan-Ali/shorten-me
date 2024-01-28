package com.shortenme.urlShortener.service;

import com.shortenme.urlShortener.Utils.BaseConverterUtil;
import com.shortenme.urlShortener.dao.UrlMappingDao;
import com.shortenme.urlShortener.dao.UrlMappingRepository;
import com.shortenme.urlShortener.entity.UrlMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class UrlMappingServiceImpl implements  UrlMappingService{

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Autowired
    private UrlMappingDao urlMappingDao;

    @Override
    public UrlMapping findById(long id) {
        return urlMappingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No url mapping found for id: " + id));
    }

    @Override
    public String insertAndReturnShort(String longUrl) {
        Long currentId = null;
        String shortKey = urlMappingDao.findShortKeyByOriginalUrl(longUrl);
        if(!ObjectUtils.isEmpty(shortKey)) {
            return shortKey;
        }
        UrlMapping urlMapping = UrlMapping.builder()
                .originalUrl(longUrl)
                .shortKey(null)
                .expirationDate(null)
                .build();
        urlMapping = urlMappingRepository.saveAndFlush(urlMapping);
        currentId = urlMapping.getId();
        shortKey = BaseConverterUtil.base10To62(currentId);
        urlMapping.setShortKey(shortKey);
        urlMappingRepository.save(urlMapping);
        return shortKey;
    }

    @Override
    public String findOriginalUrlByShortKey(String shortKey) {
        String originalUrl = null;
        // look for it in redis first.
        originalUrl = redisUtil.get(shortKey);
        if(!ObjectUtils.isEmpty(originalUrl)) {
            return originalUrl;
        }
        return urlMappingDao.findOriginalUrlByShortKey(shortKey);
    }
}
