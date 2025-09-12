package ro.scoalainformala.studentmgmt.email;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Builder
@RequiredArgsConstructor
@Slf4j
@Component
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String receiver, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(receiver);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);
            log.info("Successfully sent email to: {}", receiver);

        } catch (Exception e) {
            log.error("Failed to send email to: {}", receiver, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}



