package com.example.java_lab_3.data.dao;

import com.example.java_lab_3.data.datasets.UserDataSet;
import com.example.java_lab_3.models.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpSession;

public class UserDaoImpl implements IUserDao {
    private final Session session;

    public UserDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void add(UserProfile user) {
        session.beginTransaction();
        session.save(mapToDataSet(user));
        session.getTransaction().commit();
    }

    @Override
    public void attachSession(String userId, HttpSession httpSession) {
        UserProfile profile = getById(userId);
        UserDataSet dataSet = mapToDataSet(profile);
        dataSet.setSessionId(httpSession.getId());
        session.beginTransaction();
        session.merge(dataSet);
        session.getTransaction().commit();
    }

    @Override
    public UserProfile getById(String userId) {
        System.out.println(userId);
        return mapToProfile(session.get(UserDataSet.class, userId));
    }

    @Override
    public UserProfile getByLogin(String login) {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return mapToProfile(((UserDataSet) criteria.add(Restrictions.eq("login", login)).uniqueResult()));
    }

    @Override
    public UserProfile getByEmail(String email) {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return mapToProfile(((UserDataSet) criteria.add(Restrictions.eq("email", email)).uniqueResult()));
    }

    @Override
    public UserProfile getBySessionId(String sessionId) {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return mapToProfile(((UserDataSet) criteria.add(Restrictions.eq("sessionId", sessionId)).uniqueResult()));
    }

    @Override
    public boolean isSessionExists(String sessionId) {
        getBySessionId(sessionId);
        return getBySessionId(sessionId) != null;
    }

    @Override
    public void detachSession(String userId) {
        UserProfile profile = getById(userId);
        UserDataSet dataSet = mapToDataSet(profile);
        if (dataSet == null)
            return;
        dataSet.setSessionId(null);
        session.beginTransaction();
        session.merge(dataSet);
        session.getTransaction().commit();
    }

    private UserProfile mapToProfile(UserDataSet dataSet) {
        return dataSet == null ? null : new UserProfile(
                dataSet.getId(),
                dataSet.getLogin(),
                dataSet.getPassword(),
                dataSet.getEmail()
        );
    }

    private UserDataSet mapToDataSet(UserProfile profile) {
        return profile == null ? null : new UserDataSet(
                profile.getId(),
                profile.getLogin(),
                profile.getPassword(),
                profile.getEmail()
        );
    }
}
