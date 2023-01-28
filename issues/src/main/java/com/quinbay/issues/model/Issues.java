package com.quinbay.issues.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Issues {
    @Id
    String issueId;
    String type;
    String category;
    String sla;
    String description;
    String status;
    String comments;
    String assigneeUserId;
    Date createdDate;
    Date deadlineDate;
    Date reviewedDate;
    Date completedDate;
    Date closedDate;
}
