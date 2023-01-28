package com.quinbay.users.controller;

import com.quinbay.users.model.ReturnData;
import com.quinbay.users.model.Users;
import com.quinbay.users.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AuthService authService;

    @GetMapping("/check")
    public String sendmessage() {
        return "Connected";
    }

    @GetMapping("/viewAll")
    public List<Users> displayAllUsers() {
        System.out.println("Inside disp all user controller");
        return authService.displayAllUsers();
    }

    @GetMapping("/viewByUserId")
    public ReturnData displayByUserId(@RequestParam String userId) {
        System.out.println("Inside disp by user id controller");
        return authService.displayByUserId(userId);
    }

    @GetMapping("/authUser")
    public ReturnData authUser(@RequestParam String userId, @RequestParam String password) {
        return authService.authUser(userId, password);
    }
}