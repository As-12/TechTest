package com.workday.java;

import com.workday.java.services.JobSchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of JobRunner based on provided NaiveJobRunner solution.
 * This class handles the dequeue process of the JobQueue and delegate job execution to JobSchedulingService.
 */
public class JobRunnerImpl implements JobRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JobSchedulingService jobSchedulingService;

    private volatile boolean shouldContinue = true;

    private volatile boolean shutdownFinished = false;

    public JobRunnerImpl(final JobSchedulingService jobSchedulingService) {
        this.jobSchedulingService = jobSchedulingService;
    }

    @Override
    public void run(final JobQueue jobQueue) {
        logger.info("Calling JobRunner.run with jobQueue size of " + jobQueue.length());
        while (shouldContinue) {
            final Job job = jobQueue.pop();
            this.jobSchedulingService.executeJob(job);
        }
        logger.info("shutting down");
        shutdownFinished = true;
    }

    @Override
    public void shutdown() {
        logger.info("Calling JobRunner.shutdown");
        shouldContinue = false;
        while (!shutdownFinished) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
