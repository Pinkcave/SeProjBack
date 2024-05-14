package com.example.seprojback.controller;

import com.example.seprojback.entity.User;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.UserInfoManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoManagementController {
    @Autowired
    public UserInfoManagementService userInfoManagementService;

    @GetMapping("/userInfo/getUserInfo")
    public ResponseResult getUserInfo() {
        return userInfoManagementService.getUserInfo();
    }

    @PostMapping("/userInfo/changeRecipeState")
    public ResponseResult changeRecipeState(@RequestParam String recipeId, Integer isShow) {
        return userInfoManagementService.changeRecipeState(recipeId, isShow);
    }

    @PostMapping("/userInfo/modifyUserInfo")
    public ResponseResult modifyUserInfo(@RequestBody User user) {
        return userInfoManagementService.modifyUserInfo(user);
    }
}
