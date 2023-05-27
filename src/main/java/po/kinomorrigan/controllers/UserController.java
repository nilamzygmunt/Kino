package po.kinomorrigan.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    public UserController() {
    }

    @GetMapping(value ={"/user/login", "/login"})
    public String getLoginPage() {
        return "/user/login";
    }

    @GetMapping(value ={"/user/register", "/register"})
    public String getRegisterPage() {
        return "/user/register";
    }
}
