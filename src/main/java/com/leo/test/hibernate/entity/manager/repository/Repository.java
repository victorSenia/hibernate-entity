package com.leo.test.hibernate.entity.manager.repository;

import com.leo.test.hibernate.entity.manager.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;

/**
 * Created by Senchenko Viktor on 26.09.2016.
 */
public abstract class Repository<T> {

    public Repository(Class<T> c) {
        this.c = c;
    }

    private final Class<T> c;

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
            object=  entityManager.merge(object);
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
}