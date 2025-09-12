package ro.scoalainformala.studentmgmt.quartzconfig;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

@Slf4j
public class JobsListener implements org.quartz.JobListener {
    @Override
    public String getName() {
        return "JobsListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        log.info("Before star job");
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

        log.info("Job key: {}", jobKey);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        log.info("Operation aborted");
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

        log.info("Job key: {}", jobKey);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        log.info("Job was executed");
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

        log.info("Job key: {}", jobKey);
    }
}
