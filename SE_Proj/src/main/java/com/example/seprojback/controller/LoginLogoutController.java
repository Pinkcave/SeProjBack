package com.example.seprojback.controller;

import com.example.seprojback.entity.User;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.LoginLogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginLogoutController {

    @Autowired
    public LoginLogoutService loginLogoutService;

    @PostMapping("/user/register")
    public ResponseResult register(@RequestBody User user) {
        return loginLogoutService.register(user);
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        //登录
        return loginLogoutService.login(user);
    }

    @GetMapping("/user/logout")
    public ResponseResult logout() {
        return loginLogoutService.logout();
    }
}
