package com.quinbay.issues.controller;

import com.quinbay.issues.api.MailingAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    MailingAPI mailingAPI;

//    @GetMapping("/getAssigneeDetails")
//    public String getAssigneeDetails(@RequestParam String userId) {
//        return mailingAPI.getAssigneeDetails(userId);
//    }

    @GetMapping("/mailForOverdue")
    public String mailForOverdue(@RequestParam String issueId, @RequestParam  String userId) {
        return mailingAPI.mailForOverdue(issueId, userId);
    }

    @GetMapping("/mailForCloseRequest")
    public String mailForCloseRequest(@RequestParam String issueId){
        return mailingAPI.mailForCloseRequest(issueId);
    }
}
