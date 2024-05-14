package com.example.seprojback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.seprojback.entity.User;
import com.example.seprojback.mapper.UserMapper;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.RecipeService;
import com.example.seprojback.service.UserInfoManagementService;
import com.example.seprojback.utils.TokenOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserInfoManagementServiceImpl implements UserInfoManagementService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecipeService recipeService;
    @Override
    public ResponseResult getUserInfo() {
        try {
            String userId = TokenOperation.getUserIdFromToken();
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUserId, userId);
            User user = userMapper.selectOne(wrapper);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("name", user.getName());
            resultMap.put("mailBox", user.getMailBox());
            resultMap.put("phoneNumber", user.getPhoneNumber());
            resultMap.put("intro", user.getIntro());
            resultMap.put("gender", user.getGender());
            resultMap.put("recipe", recipeService.getRecipes(userId));

            return new ResponseResult(200, "success", resultMap);
        }
        catch (Exception e) {
            return new ResponseResult(500, e.getMessage());
        }

    }

    @Override
    public ResponseResult changeRecipeState(String recipeId, Integer isShow) {
        if (isShow == 0) {
            recipeService.recallRecipes(recipeId);
        }
        else if (isShow == 1) {
            recipeService.launchRecipe(recipeId);
        }

        return new ResponseResult(200, "success");
    }

    @Override
    public ResponseResult modifyUserInfo(User user) {
        String userId = TokenOperation.getUserIdFromToken();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId);
        updateWrapper.set("mail_box", user.getMailBox());
        updateWrapper.set("gender", user.getGender());
        updateWrapper.set("phone_number", user.getPhoneNumber());
        updateWrapper.set("intro", user.getIntro());
        userMapper.update(updateWrapper);

        return new ResponseResult(200, "success");
    }


}
