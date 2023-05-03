package com.sqlcinema.backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@Getter
@Setter
public class User {
    private int userId;
    private String email;
    private String fullName;
    private String avatarName;
    private String phoneNumber;
    private Date birthDate;
    private Date updatedAt;
}