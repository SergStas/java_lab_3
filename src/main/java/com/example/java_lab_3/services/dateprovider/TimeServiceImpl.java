package com.example.java_lab_3.services.dateprovider;

import java.sql.Timestamp;

public class TimeServiceImpl implements ITimeService {
    @Override
    public Timestamp getCurrentDate() {
        return new Timestamp(System.currentTimeMillis());
    }
}
