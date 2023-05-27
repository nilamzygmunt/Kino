package po.kinomorrigan.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    public HomePageController() {
    }

    @GetMapping(value = {"/home", "/"})
    public String getHomePage() {
        return "home";
    }
}
