package ro.scoalainformala.studentmgmt.quartzconfig;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;

@Slf4j
public class TriggersListener implements org.quartz.TriggerListener {
    @Override
    public String getName() {
        return "TriggersListener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        log.info("Trigger is starting");
        final JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

        log.info("triggerFired at{}:: jobkey:: {}", trigger.getStartTime(), jobKey);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        log.info("check Trigger");
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.info("triggerMisfired");
        final JobKey jobKey = trigger.getJobKey();

        log.info("triggerMisfired at{}:: jobkey:: {}", trigger.getStartTime(), jobKey);
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        log.info("triggerComplete");
        final JobKey jobKey = trigger.getJobKey();

        log.info("triggerMisfired at{}:: jobkey:: {}", trigger.getStartTime(), jobKey);
    }
}
