package com.shortenme.urlShortener.dao;

import com.shortenme.urlShortener.entity.UrlMapping;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Service;

@Service
public class UrlMappingDao {

    @PersistenceContext
    private EntityManager entityManager;

    public String findShortKeyByOriginalUrl(String originalUrl) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<UrlMapping> root = criteriaQuery.from(UrlMapping.class);
        criteriaQuery.select(root.get("shortKey"));

        Predicate condition = criteriaBuilder.equal(root.get("originalUrl"), originalUrl);
        criteriaQuery.where(condition);

        return entityManager.createQuery(criteriaQuery).getSingleResult();

    }

    public String findOriginalUrlByShortKey(String shortKey) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<UrlMapping> root = criteriaQuery.from(UrlMapping.class);
        criteriaQuery.select(root.get("originalUrl"));

        Predicate condition = criteriaBuilder.equal(root.get("shortKey"), shortKey);
        criteriaQuery.where(condition);
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
