package ro.scoalainformala.studentmgmt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("redirect:/web/dashboard");
        registry.addViewController("/web/login").setViewName("/auth/login");
    }
}
