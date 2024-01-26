package com.shortenme.urlShortener.dao;

import com.shortenme.urlShortener.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
}
