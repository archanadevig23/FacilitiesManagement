package com.quinbay.issues.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class ClosedIssues {
    @Id
    Integer id;
    String issueId;
    String type;
    String category;
    String sla;
    String description;
    String priority;
    String status;
    String comments;
    String assigneeUserId;
    String assigneeName;
    LocalDateTime createdDate;
    LocalDateTime deadlineDate;
    LocalDateTime reviewedDate;
    LocalDateTime completedDate;
    LocalDateTime closedDate;

    ClosedIssues() {}

    public ClosedIssues(Issues cl) {
        this.setId(cl.getId());
        this.setIssueId(cl.getIssueId());
        this.setType(cl.getType());
        this.setCategory(cl.getCategory());
        this.setSla(cl.getSla());
        this.setDescription(cl.getDescription());
        this.setPriority(cl.getPriority());
        this.setStatus(cl.getStatus());
        this.setComments(cl.getComments());
        this.setAssigneeUserId(cl.getAssigneeUserId());
        this.setAssigneeName(cl.getAssigneeName());
        this.setCreatedDate(cl.getCreatedDate());
        this.setDeadlineDate(cl.getDeadlineDate());
        this.setReviewedDate(cl.getReviewedDate());
        this.setCompletedDate(cl.getCompletedDate());
        this.setClosedDate(cl.getClosedDate());
    }

}
