package com.ssw.fssw.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping
    public String login(){
        return "view/login/login";
    }
}
