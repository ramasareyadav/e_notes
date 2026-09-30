package com.e_notes.service;

import com.e_notes.dto.UserDto;

public interface UserService {

    //public Boolean register(UserDto userDto) throws Exception;

    public Boolean register(UserDto userDto, String url) throws Exception;

}
