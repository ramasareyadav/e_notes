package com.e_notes.controller;

import com.e_notes.service.HomeService;
import com.e_notes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {


    @Autowired
    private HomeService homeService;

    @GetMapping("/verify")
    public ResponseEntity<?> varifyUserAccount(@RequestParam Integer uid, @RequestParam String code) throws Exception {
        Boolean varifyAccount = homeService.varifyAccount(uid, code);
        if (varifyAccount)

            return CommonUtil.createBuildResponseMessage("Account varification  success", HttpStatus.OK);
        return CommonUtil.createErrorResponseMessage("Invalid varification Account ", HttpStatus.BAD_REQUEST);
    }
}
