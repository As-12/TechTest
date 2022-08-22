package com.workday.java.wrappers;

import java.util.concurrent.FutureTask;

/**
 * An adapter for a PriorityRunnableJob class to be used within PriorityBlockingQueue,
 * which requires the object to be a FutureTask and implements a Comparable interface.
 */
public class PriorityRunnableJobPBQAdapter extends FutureTask<PriorityRunnableJobPBQAdapter>
        implements Comparable<PriorityRunnableJobPBQAdapter> {

    private final PriorityRunnableJob task;

    public PriorityRunnableJobPBQAdapter(final PriorityRunnableJob job) {
        super(job, null);
        this.task = job;
    }

    @Override
    public int compareTo(final PriorityRunnableJobPBQAdapter anotherJob) {
        return Integer.compare(this.task.getPriority(), anotherJob.task.getPriority());
    }

}


