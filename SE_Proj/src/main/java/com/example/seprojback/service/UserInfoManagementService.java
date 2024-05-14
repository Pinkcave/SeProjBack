package com.example.seprojback.service;


import com.example.seprojback.entity.User;
import com.example.seprojback.model.ResponseResult;


public interface UserInfoManagementService {

    ResponseResult getUserInfo();

    ResponseResult changeRecipeState(String recipeId,  Integer isShow);

    ResponseResult modifyUserInfo(User user);
}
