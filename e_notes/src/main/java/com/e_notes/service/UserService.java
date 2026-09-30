package com.e_notes.service;

import com.e_notes.dto.UserDto;
import com.e_notes.model.LoginRequest;
import com.e_notes.model.LoginResponse;

public interface UserService {

    //public Boolean register(UserDto userDto) throws Exception;

    public Boolean register(UserDto userDto, String url) throws Exception;

    public LoginResponse login(LoginRequest loginRequest);
}
