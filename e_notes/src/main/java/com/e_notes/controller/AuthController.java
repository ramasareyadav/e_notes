package com.e_notes.controller;

import com.e_notes.dto.UserDto;
import com.e_notes.service.UserService;
import com.e_notes.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {


    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) throws Exception {

        Boolean register = userService.register(userDto);

        if (Boolean.TRUE.equals(register)) {

            return CommonUtil.createBuildResponseMessage(
                    "Account registered successfully",
                    HttpStatus.CREATED
            );
        }

        return CommonUtil.createErrorResponseMessage(
                "Account registration failed",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
