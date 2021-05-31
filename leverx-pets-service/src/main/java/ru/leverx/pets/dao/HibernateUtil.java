package ru.leverx.pets.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import ru.leverx.pets.entity.Person;
import ru.leverx.pets.entity.Pet;

import java.io.*;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static final String PROPERTIES_FILE = "application.properties";

    public static SessionFactory getSessionFactory() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = HibernateUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("oh no");
        }

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, properties.getProperty("db.driver"));
                settings.put(Environment.URL, properties.getProperty("db.url"));
                settings.put(Environment.USER, properties.getProperty("db.user"));
                settings.put(Environment.PASS, properties.getProperty("db.password"));
                settings.put(Environment.DIALECT, properties.getProperty("db.dialect"));
                settings.put(Environment.SHOW_SQL, properties.getProperty("db.show_sql"));
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, properties.getProperty("db.current_session_context_class"));
                settings.put(Environment.HBM2DDL_AUTO, properties.getProperty("db.hbm2ddl_auto"));

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Pet.class);
                configuration.addAnnotatedClass(Person.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                return sessionFactory;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}