package com.e_notes.model;

import com.e_notes.dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private UserDto user;

    private String token;
}
