package com.example.java_lab_3.data.userrepository;

import com.example.java_lab_3.data.dao.IUserDao;
import com.example.java_lab_3.data.dao.UserDaoImpl;
import com.example.java_lab_3.data.datasets.UserDataSet;
import com.example.java_lab_3.models.UserProfile;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.servlet.http.HttpSession;

public class HibernateH2UserRepositoryImpl implements IUserRepository {
    private static final String hibernate_dialect = "org.hibernate.dialect.H2Dialect";
    private static final String hibernate_driver = "org.h2.Driver";
    private static final String hibernate_url = "jdbc:h2:./hibernate_h2_db";
    private static final String hibernate_username = "root";
    private static final String hibernate_password = "root";
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";

    private final SessionFactory sessionFactory;

    public HibernateH2UserRepositoryImpl() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);

        configuration.setProperty("hibernate.dialect", hibernate_dialect);
        configuration.setProperty("hibernate.connection.driver_class", hibernate_driver);
        configuration.setProperty("hibernate.connection.url", hibernate_url);
        configuration.setProperty("hibernate.connection.username", hibernate_username);
        configuration.setProperty("hibernate.connection.password", hibernate_password);
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void addUser(UserProfile user) {
        getDao().add(user);
    }

    @Override
    public void addSession(String userId, HttpSession session) {
        getDao().attachSession(userId, session);
    }

    @Override
    public UserProfile getUserBySessionId(String sessionId) {
        return getDao().getBySessionId(sessionId);
    }

    @Override
    public UserProfile getUserByLogin(String login) {
        return getDao().getByLogin(login);
    }

    @Override
    public UserProfile getUserByEmail(String email) {
        return getDao().getByEmail(email);
    }

    @Override
    public boolean isSessionRegistered(String userId) {
        return getDao().isSessionExists(userId);
    }

    @Override
    public void cleanUserSession(String userId) {
        getDao().detachSession(userId);
    }

    private IUserDao getDao() {
        return new UserDaoImpl(sessionFactory.openSession());
    }
}
