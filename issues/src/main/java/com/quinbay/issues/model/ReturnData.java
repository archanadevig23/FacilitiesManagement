package com.quinbay.issues.model;

import lombok.Data;

import java.util.List;

@Data
public class ReturnData {
    boolean status;
    String message;
    List<Issues> issues;
    Users userData;
    List<ClosedIssues> closedIssues;
}
