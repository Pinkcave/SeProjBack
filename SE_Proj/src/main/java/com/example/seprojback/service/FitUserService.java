package com.example.seprojback.service;

import com.example.seprojback.entity.FitUser;

import java.util.List;

public interface FitUserService {
    FitUser getUser(String userId);
    List<FitUser> getAllUser();
}
