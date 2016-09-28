package com.leo.test.hibernate.entity.manager.util;

import com.leo.test.hibernate.entity.manager.controller.HomeController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Senchenko Viktor on 26.09.2016.
 */
public class HibernateUtil {
    private static final EntityManagerFactory FACTORY;

    private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class.getSimpleName());

    static {
        try {
            FACTORY = Persistence.createEntityManagerFactory("com.leo.test.hibernate.entity.manager");
        } catch (Throwable ex) {
            LOGGER.log(Level.SEVERE, "EntityManagerFactory can't be created", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void main(String[] args) {
        int delete = 113;
        HomeController controller = new HomeController();
        System.out.println(controller.get(delete));
        System.out.println(controller.edit(delete, "{\"browser\":\"testhhg\", \"cssGrade\":\"test\", \"engine\":\"test\", \"engineVersion\":\"-\", \"platform\":\"-\"}"));
        System.out.println(controller.get(delete));
        controller.delete(delete);
        System.out.println(controller.create("{\"browser\":\"test\", \"cssGrade\":\"test\", \"engine\":\"test\", \"engineVersion\":\"-\", \"platform\":\"-\"}"));
        System.out.println(controller.list());
    }

    public static EntityManager entityManager() {
        return FACTORY.createEntityManager();
    }
}
