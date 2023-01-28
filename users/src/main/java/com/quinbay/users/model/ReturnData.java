package com.quinbay.users.model;

import lombok.Data;

@Data
public class ReturnData {
    boolean status;
    String message;
    Users userData;
}
