package com.workday.java.strategies;

import com.workday.java.Job;
import com.workday.java.jobs.NaiveJob;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreedyJobRankingStrategyTest {

    private GreedyJobRankingStrategy strategy;

    @Before
    public void init() {
        this.strategy = new GreedyJobRankingStrategy();
    }

    @Test
    public void testGivenSameJobTwiceShouldNotChangePriority() {
        final Job job = new NaiveJob();
        final int cost = job.duration();
        assertEquals(cost, this.strategy.calculatePriority(job));
        assertEquals(cost, this.strategy.calculatePriority(job));
    }

    @Test
    public void testGivenNegativeDurationJobShouldReturnSamePriorityAsDuration() {
        final Job job = new NaiveJob(1, -100);
        final int cost = job.duration();
        assertEquals(cost, this.strategy.calculatePriority(job));
    }
}