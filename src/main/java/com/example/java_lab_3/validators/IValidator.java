package com.example.java_lab_3.validators;

import com.example.java_lab_3.models.UserProfile;
import com.example.java_lab_3.services.accounts.enums.RegistrationResult;

public interface IValidator {
    boolean validatePath(String path);

    RegistrationResult validateNewUser(UserProfile user);
}
