package com.example.java_lab_3.services.servicelocator;

import com.example.java_lab_3.services.dateprovider.ITimeService;
import com.example.java_lab_3.services.dateprovider.TimeServiceImpl;
import com.example.java_lab_3.services.exlporerworker.DirManagerImpl;
import com.example.java_lab_3.services.exlporerworker.IDirManager;
import com.example.java_lab_3.validators.IValidator;
import com.example.java_lab_3.validators.ValidatorImpl;

public class ServiceLocatorImpl implements IServiceLocator {
    private final ITimeService _dateProvider = new TimeServiceImpl();
    private final IDirManager _pathReader = new DirManagerImpl();
    private final IValidator _validator = new ValidatorImpl();

    @Override
    public ITimeService getDateProvider() {
        return _dateProvider;
    }

    @Override
    public IDirManager getPathReader() {
        return _pathReader;
    }

    @Override
    public IValidator getValidator() {
        return _validator;
    }
}
