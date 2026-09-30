package com.e_notes.model;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
