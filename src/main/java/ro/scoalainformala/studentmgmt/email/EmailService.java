package ro.scoalainformala.studentmgmt.email;

public interface EmailService {

    void sendEmail(final String to, final String subject, final String body);
}
