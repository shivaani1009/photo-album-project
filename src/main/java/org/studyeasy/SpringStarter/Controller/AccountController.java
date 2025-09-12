package org.studyeasy.SpringStarter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.studyeasy.SpringStarter.Models.Account;
import org.studyeasy.SpringStarter.Services.AccountService;

import jakarta.validation.Valid;


@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;
    @GetMapping("/register")
    public String register(Model model){
        Account account = new Account();
        model.addAttribute("account", account);
        return "account_views/register";
    }

    @PostMapping("/register")
    public String register_user(@Valid @ModelAttribute Account account, BindingResult result){ //to bind incoming form data into a POJO

        if(result.hasErrors()){
            return "account_views/register";
        }
        accountService.save(account);
        return "account_views/register";
        }

    @GetMapping("/login")
    public String login(Model model){ //to send data to view
        return "login";
    }
}
