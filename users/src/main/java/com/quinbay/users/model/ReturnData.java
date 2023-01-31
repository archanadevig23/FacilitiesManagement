package com.quinbay.users.model;

import lombok.Data;

import java.util.Optional;

@Data
public class ReturnData {
    boolean status;
    String message;
    Users userData;
}
