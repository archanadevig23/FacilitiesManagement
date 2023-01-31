package com.quinbay.issues.service;

import com.quinbay.issues.api.IssuesAPI;
import com.quinbay.issues.model.*;
import com.quinbay.issues.repository.CategoryRepository;
import com.quinbay.issues.repository.ClosedIssueRepository;
import com.quinbay.issues.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
@Transactional
@Service
public class IssueService implements IssuesAPI {

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    ClosedIssueRepository closedIssueRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MailingService mailingService;

    @Override
    public ReturnData addIssue(IssuesInput issuesInput) {
        ReturnData returnData = new ReturnData();
        try {
            Issues issue = new Issues();
            double minutes =  Double.parseDouble(issuesInput.getSla())*60;
            issue.setType(issuesInput.getType());
            issue.setCategory(issuesInput.getCategory());
            issue.setSla(issuesInput.getSla());
            issue.setDescription(issuesInput.getDescription());
            if(Double.parseDouble(issuesInput.getSla()) <= 0.5)
                issue.setPriority("IMMEDIATE");
            else if(Double.parseDouble(issuesInput.getSla()) <= 2)
                issue.setPriority("HIGH");
            else if(Double.parseDouble(issuesInput.getSla()) <= 6)
                issue.setPriority("MEDIUM");
            else if(Double.parseDouble(issuesInput.getSla()) > 6)
                issue.setPriority("LOW");
            issue.setAssigneeUserId(issuesInput.getAssigneeUserId());
            issue.setAssigneeName(mailingService.getUserDetails(issuesInput.getAssigneeUserId()).getUserData().getUserName());
            issue.setStatus("OPEN");
            LocalDateTime now = LocalDateTime.now();
            issue.setCreatedDate(now);
            LocalDateTime deadline = now.plusMinutes((long)minutes);
            issue.setDeadlineDate(deadline);
            issueRepository.save(issue);
            String issueId = "I" + String.format("%03d" , issue.getId());
            issue.setIssueId(issueId);
            if(issue.getPriority() == "IMMEDIATE") {
                mailingService.mailForImmediates(issueId, issue.getAssigneeUserId());
            }
            issueRepository.save(issue);
            returnData.setStatus(true);
            returnData.setMessage("Issue added successfully!");
        }
        catch (Exception e) {
            System.out.println(e);
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
            returnData.setClosedIssues(closedIssueRepository.findAll());
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
        statuses.put("OVERDUE", 0);
        statuses.put("OPEN", 1);    // automatic
        statuses.put("REVIEWED", 2);    // only if user is an admin
        statuses.put("COMPLETED", 3);   // only if user is an admin
        ReturnData returnData = new ReturnData();
        try {
            if(issueRepository.findByIssueId(issueId) != null) {
                if(statuses.get(issueRepository.findByIssueId(issueId).getStatus()) <= statuses.get(status)) {
                    issueRepository.findByIssueId(issueId).setStatus(status);
                    if(statuses.get(status) == 2) {
                        issueRepository.findByIssueId(issueId).setReviewedDate(LocalDateTime.now());
                    }
                    if(statuses.get(status) == 3) {
                        issueRepository.findByIssueId(issueId).setCompletedDate(LocalDateTime.now());
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
                if(issueRepository.findByIssueId(issueId).getStatus().equals("COMPLETED")) {
                    issueRepository.findByIssueId(issueId).setStatus("CLOSED");
                    issueRepository.findByIssueId(issueId).setClosedDate(LocalDateTime.now());
                    issueRepository.save(issueRepository.findByIssueId(issueId));
                    List<Issues> closedIssues = new ArrayList<>();
                    closedIssues.add(issueRepository.findByIssueId(issueId));
                    ClosedIssues closedissue = new ClosedIssues(issueRepository.findByIssueId(issueId));
                    closedIssueRepository.save(closedissue);
                    issueRepository.deleteByIssueId(issueId);
                    returnData.setStatus(true);
                    returnData.setMessage("Issue closed!");
                    returnData.setIssues(closedIssues);
                }
                else {
                    returnData.setStatus(false);
                    returnData.setMessage("Issue has not been marked completed by admin yet. You cannot close the issue now!");
                }
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
        count.put("OVERDUE", issueRepository.findAllByStatus("OVERDUE").size());
        return count;
    }

    @Override
    public ReturnData deleteIssue(String issueId) {
        ReturnData returnData = new ReturnData();
        try {
            if(issueRepository.findByIssueId(issueId) != null) {
                if(issueRepository.findByIssueId(issueId).getStatus().equals("OPEN")) {
                    issueRepository.findByIssueId(issueId).setStatus("DELETED");
                    ClosedIssues closedIssue = new ClosedIssues(issueRepository.findByIssueId(issueId));
                    closedIssueRepository.save(closedIssue);
                    issueRepository.deleteByIssueId(issueId);
                    returnData.setStatus(true);
                    returnData.setMessage("Issue deleted!");
                }
                else {
                    returnData.setStatus(false);
                    returnData.setMessage("The issue has to be in OPEN state to be deleted!");
                }
            }
            else {
                returnData.setStatus(false);
                returnData.setMessage("Something went wrong. Couldn't delete!");
            }
        }
        catch (Exception e) {
            returnData.setStatus(false);
            returnData.setMessage("Something went wrong. Couldn't delete!");
        }
        return returnData;
    }

    @Override
    public List<Categories> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public ReturnData viewByStatus(String status) {
        ReturnData returnData = new ReturnData();
        returnData.setStatus(true);
        returnData.setMessage("Data fetched successfully");
        returnData.setIssues(issueRepository.findAllByStatus(status));
        return returnData;
    }

    @Override
    public ReturnData viewByCategories(String category) {
        ReturnData returnData = new ReturnData();
        returnData.setStatus(true);
        returnData.setMessage("Data fetched successfully");
        returnData.setIssues(issueRepository.findAllByCategory(category));
        return returnData;
    }

    @Override
    public ReturnData addCategory(String category) {
        ReturnData returnData = new ReturnData();
        System.out.println(categoryRepository.findByCategoryName(category).equals(null));
        if(categoryRepository.findByCategoryName(category).size()==0) {
            Categories categories = new Categories();
            categories.setCategoryName(category);
            categoryRepository.save(categories);
            returnData.setStatus(true);
            returnData.setMessage("Category added successfully!");
        }
        else {
            returnData.setStatus(false);
            returnData.setMessage("Category already exists");
        }
        return returnData;
    }

    @Override
    public ReturnData viewByPriority(String priority) {
        ReturnData returnData = new ReturnData();
        returnData.setStatus(true);
        returnData.setMessage("Data fetched successfully");
        System.out.println(issueRepository.findAllByPriority(priority));
        returnData.setIssues(issueRepository.findAllByPriority(priority));
        return returnData;
    }

    @Override
    public ReturnData checkForOverdues() {
        List<Issues> issues;
        LocalDateTime currDate = LocalDateTime.now();
        if(issueRepository.findAll() != null) {
            issues = issueRepository.findAll();
            for(Issues issue: issues) {
                if(currDate.isAfter(issue.getDeadlineDate())) {
                    issueRepository.findByIssueId(issue.getIssueId()).setStatus("OVERDUE");
                    mailingService.mailForOverdue(issue.getIssueId(), issue.getAssigneeUserId());
                }
            }
        }
        ReturnData returnData = new ReturnData();
        returnData.setStatus(true);
        returnData.setMessage("Overdue data fetched");
        returnData.setIssues(issueRepository.findAllByStatus("OVERDUE"));
        return returnData;
    }

    @Override
    public ReturnData checkForIncomplete() {
        List<Issues> issues;
        LocalDateTime currDate = LocalDateTime.now();
        if(issueRepository.findAll() != null) {
            issues = issueRepository.findAll();
            for(Issues issue : issues) {
                System.out.println("LIne 334 - " + currDate.isAfter(issue.getCompletedDate()));
                if(currDate.isAfter(issue.getCompletedDate())) {
                    mailingService.mailForCloseRequest(issue.getIssueId());
                }
            }
        }
        ReturnData returnData = new ReturnData();
        returnData.setStatus(true);
        returnData.setMessage("Overdue data fetched");
        returnData.setIssues(issueRepository.findAllByStatus("COMPLETED"));
        return returnData;
    }

    @Override
    public List<Issues> displayByPages(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Issues> pagedResult = issueRepository.findAll(paging);
        return pagedResult.toList();
    }
}
