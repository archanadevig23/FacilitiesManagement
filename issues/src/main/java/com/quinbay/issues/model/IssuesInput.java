package com.quinbay.issues.model;

import lombok.Data;

@Data
public class IssuesInput {
    String type;
    String category;
    String sla;
    String description;
    String assigneeUserId;
}
