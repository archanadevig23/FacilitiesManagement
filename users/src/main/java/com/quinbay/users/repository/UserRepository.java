package com.quinbay.users.repository;

import com.quinbay.users.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    ArrayList<Users> findAll();
}
