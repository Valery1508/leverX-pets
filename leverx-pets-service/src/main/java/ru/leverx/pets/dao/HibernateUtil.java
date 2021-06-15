package ru.leverx.pets.dao;

import org.hibernate.SessionException;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static java.util.Objects.isNull;
import static ru.leverx.pets.exception.ExceptionMessages.SESSION_MESSAGE;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (isNull(sessionFactory)) {
            try {
                return loadSessionFactory();
            } catch (Exception e) {
                throw new SessionException(SESSION_MESSAGE);
            }
        }
        return sessionFactory;
    }

    public static SessionFactory loadSessionFactory() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("persistenceUnit");
        return entityManagerFactory.unwrap(SessionFactory.class);
    }
}