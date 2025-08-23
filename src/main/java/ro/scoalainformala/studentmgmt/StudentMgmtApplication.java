package ro.scoalainformala.studentmgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:quartz-config.xml")
public class StudentMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentMgmtApplication.class, args);
	}
}
