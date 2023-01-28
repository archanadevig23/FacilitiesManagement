package com.quinbay.issues.api;

import com.quinbay.issues.model.Issues;
import com.quinbay.issues.model.IssuesInput;
import com.quinbay.issues.model.ReturnData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/issue")
public interface IssuesAPI {
    ReturnData addIssue(IssuesInput issuesInput);
    ReturnData viewIssuesByAssigneeId(String assigneeUserId);
    ReturnData updateStatus(String issueId, String status);
    ReturnData viewIssues();
    ReturnData closeIssue(String issueId);
    ReturnData viewClosedIssues();
    ReturnData addComments(String issueId, String comment);
    HashMap<String, Integer> countOfStatus();
}
