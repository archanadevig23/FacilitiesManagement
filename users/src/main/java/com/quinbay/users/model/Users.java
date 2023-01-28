package com.quinbay.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @NotNull
    @Id
    String userId;
    String userName;
    String password;
    String role;
    String phone;
    String email;
    String repManagerId;
}
