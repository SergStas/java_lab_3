package com.example.java_lab_3.validators;

public class ValidatorImpl implements IValidator {
    @Override
    public boolean validatePath(String path) {
        return path != null && !path.equals("");
    }
}
