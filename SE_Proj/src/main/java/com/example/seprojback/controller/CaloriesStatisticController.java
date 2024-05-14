package com.example.seprojback.controller;

import com.example.seprojback.entity.Takein;
import com.example.seprojback.model.FoodCombination;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.model.TakeInRequest;
import com.example.seprojback.service.CaloriesStatisticService;
import com.example.seprojback.service.FoodService;
import com.example.seprojback.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CaloriesStatisticController {
    @Autowired
    CaloriesStatisticService caloriesStatisticService;

    @GetMapping("/getDailyTakeIn")
    public ResponseResult getDailyTakeIn(@RequestParam String retDate){
        // 定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // 将字符串解析为Date对象
            Date date = sdf.parse(retDate);
            List<Takein> dailyTakeIn = caloriesStatisticService.getDailyCalories(date);
            Map<String,Object> retJson = new HashMap<>();
            retJson.put("success", true);
            retJson.put("meal", dailyTakeIn);
            return new ResponseResult(200, "success", retJson);

        } catch (ParseException e) {
            return new ResponseResult(500, e.getMessage());
        }
    }

    @GetMapping("/getWeekTakeIn")
    public ResponseResult getWeekTakeIn() {
        List<Float> dailyTakeIn = caloriesStatisticService.getWeekCalories();
        Map<String,Object> retJson = new HashMap<>();
        retJson.put("success", true);
        retJson.put("meal",dailyTakeIn);
        return new ResponseResult<>(200, "success", retJson);
    }

    @GetMapping("/getMonthTakeIn")
    public ResponseResult getMonthTakeIn(){
        List<Float> dailyTakeIn = caloriesStatisticService.getMonthCalories();
        Map<String,Object> retJson = new HashMap<>();
        retJson.put("success", true);
        retJson.put("meal",dailyTakeIn);
        return new ResponseResult<>(200, "success", retJson);
    }

    @GetMapping("/getPerMealTakeIn")
    public ResponseResult getPerMealTakeIn(@RequestParam int cate) {
        Takein takein = caloriesStatisticService.getDailyMealTakeIn(cate);
        List<FoodCombination> takeInFood = caloriesStatisticService.getTakeInFoodCombination(takein);
        Map<String,Object> retJson = new HashMap<>();
        retJson.put("takeIn", takein);
        retJson.put("foodCombine", takeInFood);

        return new ResponseResult<>(200,"success",retJson);
    }

    @PostMapping("/updateTakeIn")
    public ResponseResult updateTakeIn(@RequestBody TakeInRequest request){
        return caloriesStatisticService.setDailyMealTakeIn(request.getCate(),request.getFoodCombine());
    }
}
