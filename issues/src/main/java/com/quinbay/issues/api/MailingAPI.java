package com.quinbay.issues.api;

import com.quinbay.issues.model.ReturnData;

public interface MailingAPI {
    String mailForOverdue(String issueId, String userId);
    ReturnData getUserDetails(String userId);
    String mailForImmediates(String issueId, String userId);
    String mailForCloseRequest(String issueId);
}
