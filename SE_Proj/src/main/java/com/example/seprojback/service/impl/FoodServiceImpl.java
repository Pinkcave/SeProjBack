package com.example.seprojback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.seprojback.entity.Food;
import com.example.seprojback.mapper.FoodMapper;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodMapper foodMapper;
    @Override
    public List<Food> getFoodList() {
        return foodMapper.selectList(null);
    }
    @Override
    public Food getFoodDetail(String foodId){
        return foodMapper.selectById(foodId);
    }
    @Override
    public ResponseResult updateFood(Food food){
        if(foodMapper.updateById(food)<=0){
            throw new RuntimeException("食品不存在");
        }
        else{
            return new ResponseResult(200,"更新食品成功");
        }
    }
    @Override
    public ResponseResult addFood(Food food){
        LambdaQueryWrapper<Food> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Food::getFoodId).last("limit 1");
        int newId = Integer.parseInt(foodMapper.selectOne(queryWrapper).getFoodId());
        newId++;
        food.setFoodId(Integer.toString(newId));
        if(foodMapper.insert(food)<=0){
            throw new RuntimeException("食品插入失败");
        }
        else{
            return new ResponseResult(200,"食品插入成功");
        }
    }
    @Override
    public List<Food> searchFood(String name){
        LambdaQueryWrapper<Food> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Food::getName,name);
        return foodMapper.selectList(queryWrapper);
    }
    @Override
    public ResponseResult deleteFood(String foodId)
    {
        if(foodMapper.deleteById(foodId)<=0){
            throw new RuntimeException("食品不存在");
        }
        else{
            return new ResponseResult(200,"食品删除成功");
        }
    }
}
