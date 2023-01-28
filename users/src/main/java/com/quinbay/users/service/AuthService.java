package com.quinbay.users.service;

import com.quinbay.users.api.AuthAPI;
import com.quinbay.users.model.ReturnData;
import com.quinbay.users.model.Users;
import com.quinbay.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService implements AuthAPI {

    @Autowired UserRepository userRepository;

    @Override
    public ReturnData authUser(String userId, String pwd) {
        ReturnData returnData = new ReturnData();
        try {
            if(userRepository.findById(userId).get() != null ) {
                if(userRepository.findById(userId).get().getPassword().equals(pwd)) {
                    returnData.setStatus(true);
                    returnData.setMessage("Login successfully");
                    Users user = userRepository.findById(userId).get();
                    user.setPassword(null);
                    returnData.setUserData(user);
                }
                else {
                    returnData.setStatus(false);
                    returnData.setMessage("Incorrect password!");
                    returnData.setUserData(null);
                }
            }
            else {
                returnData.setStatus(false);
                returnData.setMessage("Incorrect user Id!");
                returnData.setUserData(null);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            returnData.setStatus(false);
            returnData.setMessage("Some error occurred. Kindly enter correct credentials!");
            returnData.setUserData(null);
        }
        return returnData;
    }

    @Override
    public  List<Users> displayAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public ReturnData displayByUserId(String userId) {
        ReturnData returnData = new ReturnData();
        try {
            if(userRepository.findById(userId).get()!=null) {
                returnData.setStatus(true);
                returnData.setMessage("Data fetched successfully");
                Users user = userRepository.findById(userId).get();
                user.setPassword(null);
                returnData.setUserData(user);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            returnData.setStatus(false);
            returnData.setMessage("User does not exist!");
            returnData.setUserData(null);
        }
        return returnData;
    }
}
