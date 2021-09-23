package com.example.java_lab_3.services.dateprovider;

import java.sql.Timestamp;

public class TimeService implements IDateProvider {
    @Override
    public Timestamp getCurrentDate() {
        return new Timestamp(System.currentTimeMillis());
    }
}
