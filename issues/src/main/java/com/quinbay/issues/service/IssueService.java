package com.quinbay.issues.service;

import com.quinbay.issues.api.IssuesAPI;
import com.quinbay.issues.model.Issues;
import com.quinbay.issues.model.IssuesInput;
import com.quinbay.issues.model.ReturnData;
import com.quinbay.issues.repository.ClosedIssueRepository;
import com.quinbay.issues.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.*;
@Transactional
@Service
public class IssueService implements IssuesAPI {

    static int id = 0;

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    ClosedIssueRepository closedIssueRepository;

    @Override
    public ReturnData addIssue(IssuesInput issuesInput) {
        ReturnData returnData = new ReturnData();
        try {
            Issues issue = new Issues();
            id++;
            String issueId = "I" + String.format("%03d" , id);
            issue.setIssueId(issueId);
            issue.setType(issuesInput.getType());
            issue.setCategory(issuesInput.getCategory());
            issue.setSla(issuesInput.getSla());
            issue.setDescription(issuesInput.getDescription());
            issue.setAssigneeUserId(issuesInput.getAssigneeUserId());
            issue.setStatus("OPEN");
            issue.setCreatedDate(new Date());
            issueRepository.save(issue);
            returnData.setStatus(true);
            returnData.setMessage("Issue added successfully!");
        }
        catch (Exception e) {
            returnData.setStatus(false);
            returnData.setMessage("Something went wrong. Kindly enter the issue details correctly!");
        }
        return returnData;
    }

    @Override
    public ReturnData viewIssuesByAssigneeId(String assigneeUserId) {
        ReturnData returnData = new ReturnData();
        try {
            if(issueRepository.findAllByAssigneeUserId(assigneeUserId) != null) {
                returnData.setStatus(true);
                returnData.setMessage("Issue data exists");
                returnData.setIssues(issueRepository.findAllByAssigneeUserId(assigneeUserId));
            }
        }
        catch (Exception e) {
            returnData.setStatus(false);
            returnData.setMessage("Something went wrong. Kindly enter the assignee id correctly!");
        }
        return returnData;
    }

    @Override
    public ReturnData viewIssues() {
        ReturnData returnData = new ReturnData();
        try {
            returnData.setStatus(true);
            returnData.setMessage("Data fetched successfully");
            returnData.setIssues(issueRepository.findAll());
        }
        catch (Exception e) {
            returnData.setStatus(false);
            returnData.setMessage("Something went wrong. Unable to fetch the details!");
        }
        return returnData;
    }

    @Override
    public ReturnData viewClosedIssues() {
        ReturnData returnData = new ReturnData();
        try {
            returnData.setStatus(true);
            returnData.setMessage("Data fetched successfully");
            returnData.setIssues(closedIssueRepository.findAll());
        }
        catch (Exception e) {
            returnData.setStatus(false);
            returnData.setMessage("Something went wrong. Unable to fetch the details!");
        }
        return returnData;
    }

    @Override
    public ReturnData updateStatus(String issueId, String status) {
        HashMap<String, Integer> statuses = new HashMap<String, Integer>();
        statuses.put("OPEN", 1);    // automatic
        statuses.put("REVIEWED", 2);    // only if user is an admin
        statuses.put("COMPLETED", 3);   // only if user is an admin
        ReturnData returnData = new ReturnData();
        try {
            if(issueRepository.findByIssueId(issueId) != null) {
                if(statuses.get(issueRepository.findByIssueId(issueId).getStatus()) <= statuses.get(status)) {
                    issueRepository.findByIssueId(issueId).setStatus(status);
                    if(statuses.get(status) == 2) {
                        issueRepository.findByIssueId(issueId).setReviewedDate(new Date());
                    }
                    if(statuses.get(status) == 3) {
                        issueRepository.findByIssueId(issueId).setCompletedDate(new Date());
                    }
                    issueRepository.save(issueRepository.findByIssueId(issueId));
                    System.out.println(issueRepository.findByIssueId(issueId).getStatus());
                    returnData.setStatus(true);
                    returnData.setMessage("Status updated!");
                }
                else {
                    returnData.setStatus(false);
                    returnData.setMessage("Something went wrong. Kindly enter the issue status correctly!");
                }
            }
        }
        catch (Exception e) {
            returnData.setStatus(false);
            returnData.setMessage("Something went wrong. Kindly enter the issue id correctly!");
        }
        return returnData;
    }

    @Override
    public ReturnData closeIssue(String issueId) {
        ReturnData returnData = new ReturnData();
        try {
            if(issueRepository.findByIssueId(issueId) != null) {
                issueRepository.findByIssueId(issueId).setStatus("CLOSED");
                issueRepository.findByIssueId(issueId).setClosedDate(new Date());
                issueRepository.save(issueRepository.findByIssueId(issueId));
                List<Issues> closedIssues = new ArrayList<>();
                closedIssues.add(issueRepository.findByIssueId(issueId));
                closedIssueRepository.save(issueRepository.findByIssueId(issueId));
                issueRepository.deleteByIssueId(issueId);
                returnData.setStatus(true);
                returnData.setMessage("Issue closed!");
                returnData.setIssues(closedIssues);
            }
            else {
                returnData.setStatus(false);
                returnData.setMessage("Something went wrong. Incorrect issue id!");
            }
        }
        catch (Exception e) {
            System.out.println(e);
            returnData.setStatus(false);
            returnData.setMessage("Something went wrong. Couldn't close the issue!");
        }
        return returnData;
    }

    @Override
    public ReturnData addComments(String issueId, String comment) {
        ReturnData returnData = new ReturnData();
        try {
            if(issueRepository.findByIssueId(issueId) != null) {
                issueRepository.findByIssueId(issueId).setComments(comment);
                issueRepository.save(issueRepository.findByIssueId(issueId));
                returnData.setStatus(true);
                returnData.setMessage("Comments added!");
            }
            else {
                returnData.setStatus(false);
                returnData.setMessage("Something went wrong. Couldn't add the comments!");
            }
        }
        catch (Exception e) {
            returnData.setStatus(false);
            returnData.setMessage("Something went wrong. Couldn't add the comments!");
        }
        return returnData;
    }

    @Override
    public HashMap<String, Integer> countOfStatus() {
        HashMap<String, Integer> count = new HashMap<String, Integer>();
        count.put("OPEN", issueRepository.findAllByStatus("OPEN").size());
        count.put("REVIEWED", issueRepository.findAllByStatus("REVIEWED").size());
        count.put("COMPLETED", issueRepository.findAllByStatus("COMPLETED").size());
        count.put("CLOSED", issueRepository.findAllByStatus("CLOSED").size());
        return count;
    }

}
