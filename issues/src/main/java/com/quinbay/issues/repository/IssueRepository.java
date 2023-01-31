package com.quinbay.issues.repository;

import com.quinbay.issues.model.Issues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface   IssueRepository extends JpaRepository<Issues, Integer> {
    List<Issues> findAll();
    List<Issues> findAllByAssigneeUserId(String assigneeUserId);
    Issues findByIssueId(String issueId);
    String deleteByIssueId(String issueId);
    List<Issues> findAllByStatus(String status);
    List<Issues> findAllByCategory(String category);
    List<Issues> findAllByPriority(String priority);
}
