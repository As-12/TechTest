# README

## Author
Thanaphon Chavengsaksongkram

## Design
A brief description of interface and classes.

### Primary Classes

####  JobRunnerImp.java
An implementation of JobRunner based on provided NaiveJobRunner solution. This class handles the dequeue process of the JobQueue and delegate job execution to JobSchedulingService.

#### JobSchedulingService.java
JobSchedulingService is responsible for handling job submission to the Thread Executor. The class will also throttle the task submission rate once the execution resource becomes saturated. The implementations leverage PriorityBlockingQueue in conjunction with ThreadPoolExecutor. Thread Saturation Policy sets to CallerRunsPolicy.

The highest priority job will be submitted for execution first. The process leverages a minimum heap that keeps track of the next job with the highest priority. The job of determining the priority value is delegated to a class that implements the JobRankingStrategy interface, which can be changed at runtime.

#### JobRankingStrategy.java
JobRankingStrategy is an interface for a class that determining the prioritization of each job. The project offers two implementations: FairJobRankingStrategy and GreedyJobRankingStrategy.

#### FairJobRankingStrategy.java
FairJobRankingStrategy is a strategy that aims to allow every customer to get around the same amount of execution time regardless of how many jobs are submitted.

#### GreedyJobRankingStrategy.java
GreedyJobRankingStrategy is a strategy that aims to maximize the number of jobs executed by prioritizing jobs with the shortest execution time. Thread pools work best when tasks are homogeneous and independent.

### PriorityRunnableJob.java and PriorityRunnablePBQAdapter.java
These are wrapper classes that function as an Adapter to allow Job classes to be compatible with JobSchedulingService.

### Test Classes

#### JobRunnerTest.java
Integration test for JobRunnerApplication that covers basic requirements of a JobRunner

#### FailingJob.java / NaiveJob.java
Job implementations that cover both typical and alternative scenarios where an exception was thrown during execution.

#### FairJobRankingStrategyTest.java
A simple unit test for FairJobRankingStrategy


#### GreedyJobRankingStrategyTest.java
A simple unit test for GreedyJobRankingStrategy


## Design Assumptions
Jobs are independent: they do not depend on the timing, results, or side effects of other jobs.

Jobs will eventually complete or fail.

## Changes in the pom.xml
No new dependencies are introduced. The code should be Java 8 compliant. A new configuration was added to the maven-surefire plugin to allow the test to be executed in a 16 threads environment.

## Shortcomings
A fair ranking strategy can create an unbalanced thread execution queue, potentially leading to responsiveness problems in the thread pools.

The current implementation leverage unbound waits, which can suffer from the ill effects of long-running tasks. Future improvements could introduce timed resources or bounded waits to mitigate this issue.

Integration tests rely on parallel executions and may not pass on every machine and configurations.

## Definition of fairness execution
Each customer should get around the same amount of execution time regardless of how many jobs are submitted. 
