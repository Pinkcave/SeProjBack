package com.example.seprojback.service;

import com.example.seprojback.entity.Takein;
import com.example.seprojback.entity.TakeinFoodCombination;
import com.example.seprojback.model.FoodCombination;
import com.example.seprojback.model.RawFoodCombination;
import com.example.seprojback.model.ResponseResult;

import java.util.Date;
import java.util.List;

public interface CaloriesStatisticService {
    List<Takein> getDailyCalories(Date date);
    List<Float> getWeekCalories();
    List<Float> getMonthCalories();
    Takein getDailyMealTakeIn(int cate);
    ResponseResult setDailyMealTakeIn(int cate, List<RawFoodCombination> foodCombinations);
    List<FoodCombination> getTakeInFoodCombination(Takein takeIn);
}
