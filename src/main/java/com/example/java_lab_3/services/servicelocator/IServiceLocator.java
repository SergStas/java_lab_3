package com.example.java_lab_3.services.servicelocator;

import com.example.java_lab_3.data.userrepository.IUserRepository;
import com.example.java_lab_3.services.accounts.IAccountService;
import com.example.java_lab_3.services.dateprovider.ITimeService;
import com.example.java_lab_3.services.exlporerworker.IDirManager;
import com.example.java_lab_3.validators.IValidator;

public interface IServiceLocator {
    ITimeService getDateProvider();

    IDirManager getPathReader();

    IValidator getValidator();

    IUserRepository getUserRepository();

    IAccountService getAccountService();
}
