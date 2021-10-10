package com.example.java_lab_3.data.userrepository;

import com.example.java_lab_3.models.UserProfile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryRuntimeImpl implements IUserRepository {
    private final static HashMap<String, UserProfile> userStorage = new HashMap<>();
    private final static HashMap<String, HttpSession> sessionsStorage = new HashMap<>();

    @Override
    public void addUser(UserProfile user) {
        userStorage.put(user.getId(), user);
    }

    @Override
    public void addSession(String userId, HttpSession session) {
        sessionsStorage.put(userId, session);
    }

    @Override
    public UserProfile getUserBySessionId(String sessionId) {
        if (!isSessionRegistered(sessionId))
            return null;
        String userId = sessionsStorage.entrySet().stream()
                .filter(entry -> entry.getValue().getId().equals(sessionId))
                .findFirst()
                .map(Map.Entry::getKey).orElse(null);
        if (userId == null)
            return null;
        if (userStorage.containsKey(userId))
            return userStorage.get(userId);
        return null;
    }

    @Override
    public UserProfile getUserByLogin(String login) {
        Optional<UserProfile> userProfile = userStorage.values().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
        return userProfile.orElse(null);
    }

    @Override
    public UserProfile getUserByEmail(String email) {
        Optional<UserProfile> userProfile = userStorage.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        return userProfile.orElse(null);
    }

    @Override
    public boolean isSessionRegistered(String sessionId) {
        Optional<HttpSession> foundSession = sessionsStorage.values().stream()
                .filter(session -> session.getId().equals(sessionId))
                .findFirst();
        return foundSession.isPresent();
    }

    @Override
    public void cleanUserSession(String userId) {
        sessionsStorage.remove(userId);
    }
}
