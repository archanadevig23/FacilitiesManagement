package com.quinbay.issues.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer categoryId;
    String categoryName;
}
