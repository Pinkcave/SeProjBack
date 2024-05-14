package com.example.seprojback.service;

import com.example.seprojback.entity.User;
import com.example.seprojback.model.ResponseResult;

public interface LoginLogoutService {
    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult register(User user);

}
