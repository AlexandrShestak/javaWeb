package com.shestakam.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static  SessionFactory sessionFactory = null;
    private  final static Logger logger = LogManager.getLogger(HibernateUtil.class);


    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            /*return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory(
                    new StandardServiceRegistryBuilder().build() );*/
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            SessionFactory sessionFactory = configuration
                    .buildSessionFactory(serviceRegistry);
            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            logger.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}