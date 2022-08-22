package com.workday.java.strategies;

import com.workday.java.Job;

/**
 * A strategy that sets the priority for each Job.
 */
public interface JobRankingStrategy {

    int calculatePriority(Job job);

}
