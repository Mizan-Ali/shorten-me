package com.shortenme.urlShortener.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "url_mapping")
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

    @Column(name = "short_key", unique = true, nullable = true, length = 10)
    private String shortKey;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private Timestamp creationDate;

    @Column(name = "expiration_date")
    private Timestamp expirationDate;

    @Column(name = "hits", nullable = false)
    private int hits;
}
