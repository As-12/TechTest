package com.workday.java.strategies;

import com.workday.java.Job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A fair ranking job strategy to ensure that each customer will get a similar overall execution time on their job(s).
 */
public class FairJobRankingStrategy implements JobRankingStrategy {

    private final Map<Long, Integer> priorityMap;

    public FairJobRankingStrategy() {
        this.priorityMap = new ConcurrentHashMap<>();
    }

    @Override
    public int calculatePriority(final Job job) {
        return this.priorityMap.merge(job.customerId(), Math.abs(job.duration()), Integer::sum);
    }
}
