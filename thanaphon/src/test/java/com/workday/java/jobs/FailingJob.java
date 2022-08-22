package com.workday.java.jobs;

import com.workday.java.Job;

import java.util.Random;

public class FailingJob implements Job {

    private Random random = new Random();

    private long customerId;

    private long uniqueId = random.nextLong();

    private int duration;

    private boolean executed = false;

    public FailingJob() {
        customerId = random.nextLong();
        duration = 100;
    }

    @Override
    public long customerId() {
        return customerId;
    }

    @Override
    public long uniqueId() {
        return uniqueId;
    }

    @Override
    public int duration() {
        return duration;
    }

    public boolean isExecuted() {
        return executed;
    }

    @Override
    public void execute() {
        try {
            final int number = Integer.parseInt("Bad Format");
            Thread.sleep(duration);
            executed = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
