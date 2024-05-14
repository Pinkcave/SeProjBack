package com.example.seprojback.controller;

import com.example.seprojback.entity.Food;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/food/detail")
    public ResponseResult getFoodInfo(@RequestParam String foodId) {
        return new ResponseResult(200, "success", foodService.getFoodDetail(foodId));
    }

    @PostMapping("/food/modify")
    public ResponseResult modifyFoodInfo(@RequestBody Food food) {
        return foodService.updateFood(food);
    }

    @PostMapping("/food/add")
    public ResponseResult addFoodInfo(@RequestBody Food food) {
        return foodService.addFood(food);
    }

    @PostMapping("/food/delete")
    public ResponseResult deleteFoodInfo(@RequestParam String foodId) {
        return foodService.deleteFood(foodId);
    }

    @GetMapping("/food/search")
    public ResponseResult searchFoodInfo(@RequestParam String name) {
        return new ResponseResult(200, "success", foodService.searchFood(name));
    }

}
