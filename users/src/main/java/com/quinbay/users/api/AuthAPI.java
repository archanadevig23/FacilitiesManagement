package com.quinbay.users.api;

import com.quinbay.users.model.ReturnData;
import com.quinbay.users.model.Users;

import java.util.List;

public interface AuthAPI {
    ReturnData authUser(String id, String pwd);
    List<Users> displayAllUsers();
    ReturnData displayByUserId(String id);
}
