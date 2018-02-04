package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("AuthController")
public class AuthentificationController {

    @RequestMapping("/login")
    public String loginPage(){
        return "loginPage";
    }

    @RequestMapping("/loggedIn")
    public String loggedInPage(){
        return "loggedInPage";
    }

    @RequestMapping("/logoutSuccessful")
    public String logoutSuccessfulPage(){
        return "logoutSuccessfulPage";
    }
}
