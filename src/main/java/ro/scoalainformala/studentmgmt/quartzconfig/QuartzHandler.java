package ro.scoalainformala.studentmgmt.quartzconfig;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.annotation.Configuration;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzHandler {

    private final Scheduler scheduler;

    @PostConstruct
    public void initialize() {
        try {
            scheduler.clear();
            scheduler.getListenerManager().addJobListener(new JobsListener());
            scheduler.getListenerManager().addTriggerListener(new TriggersListener());

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public <T extends Job> void addJob(Class<? extends Job> job, JobRequest jobRequest, Map params) throws SchedulerException {
        final JobDetail jobDetail = buildJobDetail(jobRequest);
        Trigger trigger = buildTrigger(jobRequest);

        registerJobInScheduler(jobDetail, trigger);

    }

    private void registerJobInScheduler(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        if (scheduler.checkExists(jobDetail.getKey())) {
            scheduler.deleteJob(jobDetail.getKey());
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    public void scheduleAssignmentReminder(JobRequest jobRequest) throws SchedulerException {
        final JobDetail jobDetail = buildJobDetail(jobRequest);
        Trigger trigger = buildTrigger(jobRequest);
        registerJobInScheduler(jobDetail, trigger);
    }


    private JobDetail buildJobDetail(JobRequest jobRequest) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("studentEmail", jobRequest.getStudentEmail());
        jobDataMap.put("assignmentName", jobRequest.getAssignmentName());
        jobDataMap.put("dueDate", jobRequest.getDueDate().toString());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(jobRequest.getName(), jobRequest.getGroup())
                .withDescription("Assignment reminder: " + jobRequest.getAssignmentName())
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }


    public List<JobResponse> findAllActivatedJobs() {
        List<JobResponse> list = new ArrayList<>();
        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                log.info("groupName: " + groupName);

                for (JobKey jobkey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    List<? extends Trigger> trigger =  scheduler.getTriggersOfJob(jobkey);

                    list.add(JobResponse.builder()
                            .jobName(jobkey.getName())
                            .groupName(jobkey.getGroup())
                            .scheduleTime(trigger.get(0).getStartTime().toString()).build());
                }
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    private Trigger buildTrigger(JobRequest jobRequest) {
        return TriggerBuilder.newTrigger()
                .forJob(jobRequest.getName(), jobRequest.getGroup())
                .startAt(fromLocalTimeToDate(jobRequest.getTriggerTime()))
                .withDescription("Trigger for " + jobRequest.getAssignmentName())
                .build();
    }

    private Date fromLocalTimeToDate(final LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }
}
