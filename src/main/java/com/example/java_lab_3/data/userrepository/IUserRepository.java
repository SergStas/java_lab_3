package com.example.java_lab_3.data.userrepository;

import com.example.java_lab_3.models.UserProfile;

import javax.servlet.http.HttpSession;

public interface IUserRepository {
    void addUser(UserProfile user);

    void addSession(String userId, HttpSession session);

    UserProfile getUserById(String userId);

    UserProfile getUserByLogin(String login);

    UserProfile getUserByEmail(String email);

    HttpSession getUserSessionById(String userId);

    void cleanUserSession(String userId);
}
