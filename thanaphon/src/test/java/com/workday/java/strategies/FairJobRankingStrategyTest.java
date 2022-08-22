package com.workday.java.strategies;

import com.workday.java.Job;
import com.workday.java.jobs.NaiveJob;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FairJobRankingStrategyTest {

    private FairJobRankingStrategy strategy;

    @Before
    public void init() {
        this.strategy = new FairJobRankingStrategy();
    }

    @Test
    public void testGivenSameJobTwiceShouldDoublePriority() {
        final Job job = new NaiveJob();
        final int cost = job.duration();
        assertEquals(cost, this.strategy.calculatePriority(job));
        assertEquals(cost + cost, this.strategy.calculatePriority(job));
    }

    @Test
    public void testGivenJobWithNegativeDurationShouldReturnPositivePriority() {
        final Job job = new NaiveJob(1, -100);
        final int cost = job.duration();
        assertEquals(-1 * cost, this.strategy.calculatePriority(job));
    }
}
