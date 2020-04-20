package com.oauth2.demo.controller;

import com.oauth2.demo.model.Oauth2Properties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @PostMapping("/login")
    public String login(){
        return "Login Success!";
    }

    @GetMapping("/user/r1")
    public String getResource1(){
        return "Get Resource 1 Success!";
    }

    @GetMapping("/user/r2")
    public String getResource2(){
        return "Get Resource 2 Success!";
    }

    @PostMapping("/logout")
    public String logout(){
        return "Logout Success!";
    }


}
