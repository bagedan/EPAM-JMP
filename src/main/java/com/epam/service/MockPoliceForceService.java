package com.epam.service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alexey on 25.04.2016.
 */
public class MockPoliceForceService implements PoliceForceService {
    private final long maxExecutionTime;

    public MockPoliceForceService(long maxExecutionTime) {
        this.maxExecutionTime = maxExecutionTime;
    }

    @Override
    public String getContactNo(String forceName) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(maxExecutionTime));
        } catch (InterruptedException e) {
            return "";
        }
        return "asdf";
    }
}
