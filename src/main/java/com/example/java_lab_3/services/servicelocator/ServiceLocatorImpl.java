package com.example.java_lab_3.services.servicelocator;

import com.example.java_lab_3.data.userrepository.IUserRepository;
import com.example.java_lab_3.data.userrepository.UserRepositoryRuntimeImpl;
import com.example.java_lab_3.services.accounts.AccountServiceImpl;
import com.example.java_lab_3.services.accounts.IAccountService;
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
    private final IUserRepository repository = new UserRepositoryRuntimeImpl();
    private final IAccountService accountService = new AccountServiceImpl(repository);

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

    @Override
    public IUserRepository getUserRepository() {
        return repository;
    }

    @Override
    public IAccountService getAccountService() {
        return accountService;
    }
}
