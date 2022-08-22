package com.workday.java.strategies;

import com.workday.java.Job;

/**
 * A greedy ranking job strategy which priority jobs with the shortest durations.
 */
public class GreedyJobRankingStrategy implements JobRankingStrategy {

    @Override
    public int calculatePriority(final Job job) {
        return job.duration();
    }

}
