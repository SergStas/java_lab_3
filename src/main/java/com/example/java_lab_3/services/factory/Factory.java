package com.example.java_lab_3.services.factory;

import com.example.java_lab_3.services.dateprovider.IDateProvider;
import com.example.java_lab_3.services.dateprovider.TimeService;
import com.example.java_lab_3.services.exlporerworker.DirectoriesListProvider;
import com.example.java_lab_3.services.exlporerworker.IPathReader;
import com.example.java_lab_3.validators.IValidator;
import com.example.java_lab_3.validators.Validator;

public class Factory implements IFactory {
    private final IDateProvider _dateProvider = new TimeService();
    private final IPathReader _pathReader = new DirectoriesListProvider();
    private final IValidator _validator = new Validator();

    @Override
    public IDateProvider getDateProvider() {
        return _dateProvider;
    }

    @Override
    public IPathReader getPathReader() {
        return _pathReader;
    }

    @Override
    public IValidator getValidator() {
        return _validator;
    }
}
