package jm.task.core.jdbc.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Util {

    private static Configuration configuration;

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            ConfigLoader configLoader = new ConfigLoader("application.properties");
            configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.DRIVER, configLoader.getProperty("db.driver"));
            settings.put(Environment.URL, configLoader.getProperty("db.url"));
            settings.put(Environment.USER, configLoader.getProperty("db.user"));
            settings.put(Environment.PASS, configLoader.getProperty("db.password"));
            settings.put(Environment.DIALECT, configLoader.getProperty("db.dialect"));
            settings.put(Environment.SHOW_SQL, configLoader.getProperty("db.show_sql"));
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, configLoader.getProperty("db.current_session_context_class"));
            settings.put(Environment.HBM2DDL_AUTO, configLoader.getProperty("db.hbm2ddl_auto"));

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get("jdbc:postgresql://localhost:5432/customers"),
                    PropertiesUtil.get("postgres"),
                    PropertiesUtil.get("1111")
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public static Session openSession() {
        return sessionFactory.openSession();
    }

    public static void shutdown() {

        sessionFactory.close();
    }
}