package com.example.java_lab_3.services.accounts;

import com.example.java_lab_3.data.userrepository.IUserRepository;
import com.example.java_lab_3.models.UserProfile;
import com.example.java_lab_3.services.accounts.enums.LoginResult;
import com.example.java_lab_3.services.accounts.enums.RegistrationResult;
import com.example.java_lab_3.servlets.MainServlet;
import com.example.java_lab_3.validators.IValidator;

import javax.servlet.http.HttpSession;

public class AccountServiceImpl implements IAccountService {
    private final IUserRepository userRepository;

    public AccountServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegistrationResult registerUser(UserProfile user) {
        IValidator validator = MainServlet.SERVICE_LOCATOR.getValidator();

        UserProfile profileByLogin = userRepository.getUserByLogin(user.getLogin());
        UserProfile profileByEmail = userRepository.getUserByEmail(user.getEmail());
        if (profileByEmail != null)
            return RegistrationResult.EMAIL_IS_OCCUPIED;
        if (profileByLogin != null)
            return RegistrationResult.LOGIN_IS_OCCUPIED;
        RegistrationResult validationResult = validator.validateNewUser(user);
        if (validationResult != RegistrationResult.OK)
            return validationResult;

        userRepository.addUser(user);
        return RegistrationResult.OK;
    }

    @Override
    public boolean isUserAuthorized(String sessionId) {
        return userRepository.isSessionRegistered(sessionId);
    }

    @Override
    public LoginResult logInUser(UserProfile inputProfile, HttpSession session) {
        UserProfile foundProfile = inputProfile.getLogin() != null ?
                userRepository.getUserByLogin(inputProfile.getLogin()) :
                userRepository.getUserByEmail(inputProfile.getEmail());
        if (foundProfile == null)
            return LoginResult.PROFILE_NOT_FOUND;
        if (inputProfile.getId() == null)
            inputProfile.setId(foundProfile.getLogin());
        if (!foundProfile.getPassword().equals(inputProfile.getPassword()))
            return LoginResult.INCORRECT_PASSWORD;

        userRepository.addSession(inputProfile.getId(), session);
        return LoginResult.OK;
    }

    @Override
    public boolean logOut(String sessionId) {
        UserProfile user = getUserBySessionId(sessionId);
        userRepository.cleanUserSession(user.getId());
        return true;
    }

    @Override
    public UserProfile getUserBySessionId(String sessionId) {
        return userRepository.getUserBySessionId(sessionId);
    }
}
