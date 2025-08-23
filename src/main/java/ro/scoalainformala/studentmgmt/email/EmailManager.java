package ro.scoalainformala.studentmgmt.email;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Data
@Component
public class EmailManager {

    private final String PASSWORD = "password";
    private final String SENDER_EMAIL = "your@email.com";
    private final Properties PROPS = getProperties();

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return properties;
    }
}
