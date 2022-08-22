package com.workday.java.services;

import com.workday.java.Job;
import com.workday.java.strategies.JobRankingStrategy;
import com.workday.java.wrappers.PriorityRunnableJob;
import com.workday.java.wrappers.PriorityRunnableJobPBQAdapter;

import java.util.concurrent.*;

/**
 * JobSchedulingService is a service class that allows a job to be submitted with varying priorities.
 * The job with the highest priority will be submitted for execution first.
 *
 * The job execution queue can become saturated when there are too many jobs submitted for available resources, which
 * cause the execution to become throttle by running the task on the thread as the JobSchedulingService.
 */
public class JobSchedulingService {

    private final Executor threadExecutor;

    private final JobRankingStrategy rankingStrategy;

    /**
     * Creates a new JobSchedulingService with the given initial parameters
     *
     * @param rankingStrategy the optimization strategy for setting priority for each task.
     * @param queueSize       the maximum number of jobs allowed in the queue. This queue will hold only the Runnable tasks submitted by the executeJob method.
     * @param minThread       the number of threads to keep in the execution pool, even if they are idle.
     * @param maxThread       the maximum number of threads to allow in the execution pool
     * @param keepAliveTime   when the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
     */
    public JobSchedulingService(final JobRankingStrategy rankingStrategy, final int queueSize, final int minThread, final int maxThread, final long keepAliveTime) {
        final BlockingQueue<Runnable> queue = new PriorityBlockingQueue<>(queueSize);
        final RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        this.threadExecutor = new ThreadPoolExecutor(minThread, maxThread, keepAliveTime, TimeUnit.SECONDS, queue, rejectedExecutionHandler);
        this.rankingStrategy = rankingStrategy;
    }

    /**
     * Add job to the execution pool.
     *
     * @param job the job to be executed.
     */
    public void executeJob(final Job job) {
        final int priority = this.rankingStrategy.calculatePriority(job);
        final PriorityRunnableJobPBQAdapter nextJob = new PriorityRunnableJobPBQAdapter(new PriorityRunnableJob(job, priority));
        this.threadExecutor.execute(nextJob);
    }
}
