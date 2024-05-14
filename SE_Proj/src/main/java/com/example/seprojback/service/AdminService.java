package com.example.seprojback.service;

import com.example.seprojback.entity.Food;
import com.example.seprojback.model.ResponseResult;


public interface AdminService {
    ResponseResult modifyFood(Food food);
    ResponseResult auditComment(String commentId, boolean result);
    ResponseResult auditRecipe(String recipeId, boolean result);
}
