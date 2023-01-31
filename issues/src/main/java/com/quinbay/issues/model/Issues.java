package com.quinbay.issues.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Issues {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}
