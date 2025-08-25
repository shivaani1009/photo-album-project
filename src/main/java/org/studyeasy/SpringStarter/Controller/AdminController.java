package org.studyeasy.SpringStarter.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.studyeasy.SpringStarter.Models.Account;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String admin(@ModelAttribute Account account){ //to bind incoming form data into a POJO
        return "admin";
    }
}
