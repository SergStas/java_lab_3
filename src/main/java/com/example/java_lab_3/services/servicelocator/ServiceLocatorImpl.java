package com.example.java_lab_3.services.servicelocator;

import com.example.java_lab_3.data.userrepository.H2UserRepositoryImpl;
import com.example.java_lab_3.data.userrepository.HibernateH2UserRepositoryImpl;
import com.example.java_lab_3.data.userrepository.IUserRepository;
import com.example.java_lab_3.services.accounts.AccountServiceImpl;
import com.example.java_lab_3.services.accounts.IAccountService;
import com.example.java_lab_3.services.dateprovider.ITimeService;
import com.example.java_lab_3.services.dateprovider.TimeServiceImpl;
import com.example.java_lab_3.services.exlporerworker.DirManagerImpl;
import com.example.java_lab_3.services.exlporerworker.IDirManager;
import com.example.java_lab_3.validators.IValidator;
import com.example.java_lab_3.validators.ValidatorImpl;

public class ServiceLocatorImpl implements IServiceLocator {
    private final ITimeService dateProvider;
    private final IDirManager pathReader;
    private final IValidator validator;
    private final IUserRepository repository;
    private final IAccountService accountService;

    public ServiceLocatorImpl() {
        dateProvider = new TimeServiceImpl();
        pathReader = new DirManagerImpl();
        validator = new ValidatorImpl();
//        repository = new H2UserRepositoryImpl();
        repository = new HibernateH2UserRepositoryImpl();
        accountService = new AccountServiceImpl(repository);
    }

    @Override
    public ITimeService getDateProvider() {
        return dateProvider;
    }

    @Override
    public IDirManager getPathReader() {
        return pathReader;
    }

    @Override
    public IValidator getValidator() {
        return validator;
    }

    @Override
    public IUserRepository getUserRepository() {
        return repository;
    }

    @Override
    public IAccountService getAccountService() {
        return accountService;
    }
}
