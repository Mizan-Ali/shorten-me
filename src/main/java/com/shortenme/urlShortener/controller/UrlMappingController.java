package com.shortenme.urlShortener.controller;

import com.shortenme.urlShortener.dto.ApiResponse;
import com.shortenme.urlShortener.entity.UrlMapping;
import com.shortenme.urlShortener.service.UrlMappingService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@RestController
@RequestMapping("")
public class UrlMappingController {

    @Autowired
    private UrlMappingService urlMappingService;

    @GetMapping
    @RequestMapping("/id/{id}")
    public ResponseEntity<UrlMapping> getById(@PathVariable(value = "id") long id) {
        try {
            UrlMapping urlMapping = urlMappingService.findById(id);
            if (urlMapping != null) {
                return ResponseEntity.ok(urlMapping);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{shortKey}")
    public void redirectToOriginalUrl(@PathVariable String shortKey, HttpServletResponse response) throws IOException {
        try {
            String originalUrl = null;
            originalUrl = urlMappingService.findOriginalUrlByShortKey(shortKey);
            if(originalUrl == null) {
                // If the original URL is not found, you might want to redirect to a default page or show an error
                response.sendRedirect("/defaultPage");
            }
            // If the original URL is found, async increment "hit" counter and redirect to it
            // kafka.incrementHitCounter(originalUrl);

            response.sendRedirect(originalUrl);
            return;
        } catch (Exception e) {
            // Handle exceptions if necessary
        }

        // If the original URL is not found, you might want to redirect to a default page or show an error
        response.sendRedirect("/defaultPage");
    }

    @PostMapping
    @RequestMapping("/fetchshorturl")
    public ApiResponse<String> createShortUrl(@RequestBody String longUrl) {
        ApiResponse<String> response = new ApiResponse<>();
        if(StringUtils.isEmpty(longUrl)) {
            response.setMessage("long url cannot be empty");
            return response;
        }
        String shortUrl =  urlMappingService.insertAndReturnShort(longUrl);
        response.setBody(shortUrl);
        response.setMessage("Long Url: " + longUrl);
        return response;
    }

}
