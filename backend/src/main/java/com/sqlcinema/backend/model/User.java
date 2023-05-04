package com.sqlcinema.backend.model;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private int userId;
    private String email;
    private String fullName;
    private String avatarName;
    private String phoneNumber;
    private Date birthDate;
    private Date updatedAt;
}