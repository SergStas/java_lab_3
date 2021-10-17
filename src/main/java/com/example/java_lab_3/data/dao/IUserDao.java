package com.example.java_lab_3.data.dao;

import com.example.java_lab_3.models.UserProfile;

import javax.servlet.http.HttpSession;

public interface IUserDao {
    void add(UserProfile user);

    void attachSession(String userId, HttpSession session);

    UserProfile getById(String userId);

    UserProfile getByLogin(String login);

    UserProfile getByEmail(String email);

    UserProfile getBySessionId(String sessionId);

    boolean isSessionExists(String sessionId);

    void detachSession(String userId);
}
