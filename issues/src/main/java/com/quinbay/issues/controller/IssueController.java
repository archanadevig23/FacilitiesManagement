package com.quinbay.issues.controller;

import com.quinbay.issues.api.IssuesAPI;
import com.quinbay.issues.model.Categories;
import com.quinbay.issues.model.Issues;
import com.quinbay.issues.model.IssuesInput;
import com.quinbay.issues.model.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/issue")
public class IssueController {

    @Autowired
    IssuesAPI issuesAPI;

    @GetMapping("/check")
    public String sendmessage() {
        return "Connected";
    }

    @PostMapping("/add")
    public ReturnData addIssue(@RequestBody IssuesInput issuesInput) {

        return issuesAPI.addIssue(issuesInput);
    }

    @GetMapping("/viewIssuesByAssigneeId/{assigneeUserId}")
    public ReturnData viewIssuesByAssigneeId(@PathVariable String assigneeUserId) {
        return issuesAPI.viewIssuesByAssigneeId(assigneeUserId);
    }

    @PutMapping("/updateStatus")
    public ReturnData updateStatus(@RequestParam String issueId, @RequestParam String status) {
        System.out.println("Inside update status controller");
        return issuesAPI.updateStatus(issueId, status);
    }

    @GetMapping("/viewAll")
    public ReturnData viewIssues() {
        return issuesAPI.viewIssues();
    }

    @PostMapping("/close")
    public ReturnData closeIssue(String issueId) {
        return issuesAPI.closeIssue(issueId);
    }

    @GetMapping("/viewClosedIssues")
    public ReturnData viewClosedIssues() {
        return issuesAPI.viewClosedIssues();
    }

    @PutMapping("/addComment")
    public ReturnData addComments(@RequestParam String issueId, @RequestParam String comment) {
        return issuesAPI.addComments(issueId, comment);
    }

    @GetMapping("/getCount")
    public HashMap<String, Integer> countOfStatus() {
        return issuesAPI.countOfStatus();
    }

    @DeleteMapping("/deleteIssue")
    public ReturnData deleteIssue(@RequestParam String issueId) {
        return issuesAPI.deleteIssue(issueId);
    }

    @GetMapping("/getCategories")
    public List<Categories> getCategories() {
        return issuesAPI.getCategories();
    }

    @GetMapping("/viewByStatus")
    public ReturnData viewByStatus(@RequestParam String status) {
        return issuesAPI.viewByStatus(status);
    }

    @GetMapping("/viewByCategories")
    public ReturnData viewByCategories(@RequestParam String category) {
        return issuesAPI.viewByCategories(category);
    }

    @PostMapping("/addCategory")
    public ReturnData addCategory(String category) {
        return issuesAPI.addCategory(category);
    }

    @GetMapping("/viewByPriority")
    public ReturnData viewByPriority(@RequestParam String priority) {
        return issuesAPI.viewByPriority(priority);
    }

    @GetMapping("/checkForOverdues")
    public ReturnData checkForOverdues() {
        return issuesAPI.checkForOverdues();
    }

    @GetMapping("/checkForIncomplete")
    public ReturnData checkForIncomplete() {
        return issuesAPI.checkForIncomplete();
    }

    @GetMapping("/details/{pageNo}")
    public List<Issues> displayByPages(@PathVariable int pageNo) {
        int pageSize = 5;
        return issuesAPI.displayByPages(pageNo, pageSize);
    }
}
