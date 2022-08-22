package com.workday.java.wrappers;

import com.workday.java.Job;

/**
 * A Job wrapper class which implements both Priority and Runnable Interfaces.
 */
public class PriorityRunnableJob implements Runnable {

    private int priority;

    private final Job task;

    public PriorityRunnableJob(final Job job, final int priority) {
        this.task = job;
        this.priority = priority;
    }

    @Override
    public void run() {
        this.task.execute();
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(final int priority) {
        this.priority = priority;
    }
}