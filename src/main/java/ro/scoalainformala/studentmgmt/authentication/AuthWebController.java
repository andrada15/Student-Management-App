package ro.scoalainformala.studentmgmt.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class AuthWebController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "/auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "/auth/register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
