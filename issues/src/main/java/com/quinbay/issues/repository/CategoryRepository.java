package com.quinbay.issues.repository;

import com.quinbay.issues.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Categories, Integer> {
    List<Categories> findAll();
    List<Categories> findByCategoryName(String category);
}