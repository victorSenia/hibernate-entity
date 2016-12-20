package com.leo.test.hibernate.entity.manager.repository;

import com.leo.test.hibernate.entity.manager.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;

/**
 * Created by Senchenko Viktor on 26.09.2016.
 */
public abstract class Repository<T> {

    private final Class<T> c;

    public Repository(Class<T> c) {
        this.c = c;
    }

    private EntityManager entityManager() {
        return HibernateUtil.entityManager();
    }

    public Iterable<T> list() {
        EntityManager entityManager = entityManager();
        CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(c);
        query.select(query.from(c));
        return entityManager.createQuery(query).getResultList();
    }

    public T get(Serializable id) {
        return entityManager().find(c, id);
    }

    public T edit(T object) {
        EntityManager entityManager = entityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            object = entityManager.merge(object);
            transaction.commit();
            return object;
        } finally {
            entityManager.close();
        }
    }

    public void delete(Serializable id) {
        EntityManager entityManager = entityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T object = entityManager.find(c, id);
            entityManager.remove(object);
            transaction.commit();
        } finally {
            entityManager.close();
        }
    }

    protected Iterable<T> custom(Customisation<T> customisation) {
        EntityManager entityManager = entityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(c);
        Root<T> root = query.from(c);
        customisation.custom(criteriaBuilder, query, root);
        return entityManager.createQuery(query).getResultList();
    }

    protected interface Customisation<T> {
        void custom(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> query, Root<T> root);
    }
}