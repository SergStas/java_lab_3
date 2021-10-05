package com.example.java_lab_3.services.accounts;

import com.example.java_lab_3.models.UserProfile;
import com.example.java_lab_3.services.accounts.enums.LoginResult;
import com.example.java_lab_3.services.accounts.enums.RegistrationResult;

import javax.servlet.http.HttpSession;

public interface IAccountService {
    RegistrationResult registerUser(UserProfile user);

    boolean isUserAuthorized(String sessionId);

    LoginResult logInUser(UserProfile userProfile, HttpSession session);

    boolean logOut(String userId);

    UserProfile getUserBySessionId(String userId);
}
