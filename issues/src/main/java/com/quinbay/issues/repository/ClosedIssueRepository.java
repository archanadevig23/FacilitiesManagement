package com.quinbay.issues.repository;

import com.quinbay.issues.model.ClosedIssues;
import com.quinbay.issues.model.Issues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClosedIssueRepository extends JpaRepository<ClosedIssues, Integer> {
    List<ClosedIssues> findAll();
}
