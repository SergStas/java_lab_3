package com.example.java_lab_3.services.factory;

import com.example.java_lab_3.services.dateprovider.IDateProvider;
import com.example.java_lab_3.services.exlporerworker.IPathReader;
import com.example.java_lab_3.validators.IValidator;

public interface IFactory {
    IDateProvider getDateProvider();

    IPathReader getPathReader();

    IValidator getValidator();
}
