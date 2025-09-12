package ro.scoalainformala.studentmgmt.quartzconfig;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.scoalainformala.studentmgmt.email.EmailService;

@Slf4j
@Component
public class EmailJob implements Job {
    private final EmailService emailService;


    @Autowired
    public EmailJob(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        String studentEmail = jobDataMap.getString("studentEmail");
        String assignmentName = jobDataMap.getString("assignmentName");
        String dueDate = jobDataMap.getString("dueDate");

        String subject = "Assignment due: " + assignmentName;
        String body = String.format("Assignment %s due at: %s", assignmentName, dueDate);

        emailService.sendEmail(studentEmail, subject, body);
    }
}
