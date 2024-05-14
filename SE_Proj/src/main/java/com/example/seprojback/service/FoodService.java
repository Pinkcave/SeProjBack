package com.example.seprojback.service;

import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.entity.Food;

import java.util.List;

public interface FoodService {
    List<Food> getFoodList();
    Food getFoodDetail(String foodId);
    ResponseResult updateFood(Food food);
    ResponseResult addFood(Food food);
    List<Food> searchFood(String name);
    ResponseResult deleteFood(String foodId);
}
