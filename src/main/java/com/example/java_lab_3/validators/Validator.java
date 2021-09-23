package com.example.java_lab_3.validators;

public class Validator implements IValidator {
    @Override
    public boolean validatePath(String path) {
        return path != null && !path.equals("");
    }
}
