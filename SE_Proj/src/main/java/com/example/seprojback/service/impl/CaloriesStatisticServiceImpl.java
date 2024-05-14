package com.example.seprojback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.seprojback.entity.Food;
import com.example.seprojback.entity.Takein;
import com.example.seprojback.entity.TakeinFoodCombination;
import com.example.seprojback.mapper.TakeInFoodCombinationMapper;
import com.example.seprojback.mapper.TakeInMapper;
import com.example.seprojback.model.FoodCombination;
import com.example.seprojback.model.RawFoodCombination;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.CaloriesStatisticService;
import com.example.seprojback.utils.TokenOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class CaloriesStatisticServiceImpl implements CaloriesStatisticService {
    @Autowired
    private TakeInMapper takeInMapper;

    @Autowired
    private TakeInFoodCombinationMapper takeinFoodCombinationMapper;

    @Override
    public List<Takein> getDailyCalories(Date date) {
        LambdaQueryWrapper<Takein> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Takein::getDate, date);
        return takeInMapper.selectList(queryWrapper);
    }

    @Override
    public List<Float> getWeekCalories() {
        // 实例化一个calendar类对象，设置为GMT+8，00:00:00
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.DAY_OF_YEAR, 1-calendar.get(Calendar.DAY_OF_WEEK));
        Date lastSunday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 6);
        Date lastSaturday = calendar.getTime();

        // 查表
        LambdaQueryWrapper<Takein> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(Takein::getDate, lastSunday, lastSaturday).orderByAsc(Takein::getDate);
        List<Takein> rawList = takeInMapper.selectList(queryWrapper);

        if(rawList.isEmpty()){
            return null;
        }
        else {
            List<Float> newList = new LinkedList<>();
            Calendar firstDate = Calendar.getInstance(timeZone);
            firstDate.set(Calendar.HOUR_OF_DAY, 0);
            firstDate.set(Calendar.MINUTE, 0);
            firstDate.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            firstDate.setTime(lastSunday);
            for(int j = 0, i = 0; j < 7; j++) {
                float dayCalories = (float) 0;
                Date currentDate = firstDate.getTime();

                if(i<rawList.size() && Objects.equals(rawList.get(i).getDate(), currentDate)){
                    while(i<rawList.size() && Objects.equals(rawList.get(i).getDate(), currentDate)){
                        dayCalories += rawList.get(i).getCalories();
                        i+=1;
                    }
                    newList.add(dayCalories);
                }
                else{
                    newList.add((float) 0);
                }
                firstDate.add(Calendar.DAY_OF_YEAR, 1);
            }
            return newList;
        }
    }

    @Override
    public List<Float> getMonthCalories() {
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // 设置为上一个月
        calendar.add(Calendar.MONTH, -1);
        // 获取上一月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDay = calendar.getTime();
        // 获取上一月最后一天
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDay = calendar.getTime();

        LambdaQueryWrapper<Takein> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(Takein::getDate, firstDay, lastDay).orderByAsc(Takein::getDate);
        List<Takein> rawList = takeInMapper.selectList(queryWrapper);

        if(rawList.isEmpty()){
            return null;
        }
        else {
            List<Float> newList = new LinkedList<>();
            Calendar firstDate = Calendar.getInstance(timeZone);
            firstDate.set(Calendar.HOUR_OF_DAY, 0);
            firstDate.set(Calendar.MINUTE, 0);
            firstDate.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            firstDate.setTime(firstDay);
            // 转换为LocalDate
            LocalDate localDate1 = firstDay.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            LocalDate localDate2 = lastDay.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

            // 计算两个日期之间的差距
            long daysDifference = ChronoUnit.DAYS.between(localDate1, localDate2);
            for(int j = 0, i = 0; j <= daysDifference; j++) {
                Date currentDate = firstDate.getTime();
                float dayCalories = (float) 0;
                if(i<rawList.size() && Objects.equals(rawList.get(i).getDate(), currentDate)){
                    while(i<rawList.size() && Objects.equals(rawList.get(i).getDate(), currentDate)){
                        dayCalories+=rawList.get(i).getCalories();
                        i+=1;
                    }
                    newList.add(dayCalories);
                }
                else{
                    newList.add((float) 0);
                }
                firstDate.add(Calendar.DAY_OF_YEAR, 1);
            }
            return newList;
        }
    }

    @Override
    public Takein getDailyMealTakeIn(int cate) {
        String userId = TokenOperation.getUserIdFromToken();
        // 实例化一个calendar类对象，设置为GMT+8，00:00:00
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        LambdaQueryWrapper<Takein> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Takein::getDate, date).eq(Takein::getCategory, cate).eq(Takein::getUserId, userId);
        Takein dailyMealTakeIn = takeInMapper.selectOne(queryWrapper);
        if(dailyMealTakeIn == null){
            Takein newTakeIn = new Takein();
            newTakeIn.setDate(date);
            newTakeIn.setCategory(cate);
            newTakeIn.setUserId(userId);
            newTakeIn.setCalories((float) 0);
            return newTakeIn;
        }
        else{
            return dailyMealTakeIn;
        }
    }

    @Override
    public ResponseResult setDailyMealTakeIn(int cate, List<RawFoodCombination> foodCombinations) {
        String userId = TokenOperation.getUserIdFromToken();
        // 获取takeIn的id
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        // dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String dateString = dateFormat.format(date);
        String takeInId = userId + dateString + cate;
        Takein existedTakeIn = takeInMapper.selectById(takeInId);


        // 计算热量总和
        float calories = (float) 0;
        for(RawFoodCombination food: foodCombinations){
            float oneCalories = food.getCalories() * food.getAmount();
            calories += oneCalories;
        }
        // 修改TakeIn变量
        Takein newTakeIn = new Takein();
        newTakeIn.setId(takeInId);
        newTakeIn.setUserId(userId);
        newTakeIn.setCategory(cate);
        newTakeIn.setCalories(calories);
        newTakeIn.setDate(date);
        if(existedTakeIn == null) {
            takeInMapper.insert(newTakeIn);
        }
        else{
            takeInMapper.updateById(newTakeIn);
        }
        // 修改摄入细节
        LambdaQueryWrapper<TakeinFoodCombination> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TakeinFoodCombination::getTakeinId, takeInId);
        takeinFoodCombinationMapper.delete(lambdaQueryWrapper);
        for(RawFoodCombination food: foodCombinations){
            TakeinFoodCombination takeinFoodCombination = new TakeinFoodCombination();

            takeinFoodCombination.setAmount(food.getAmount());
            takeinFoodCombination.setTakeinId(takeInId);
            takeinFoodCombination.setFoodId(food.getFoodId());
            takeinFoodCombinationMapper.insert(takeinFoodCombination);
        }

        return new ResponseResult(200,"更新摄入成功");
    }

    @Override
    public List<FoodCombination> getTakeInFoodCombination(Takein takeIn) {
        if(takeIn.getCalories() < 10e-6){
            return null;
        }
        List<Map<String, Object>> combinationList = takeinFoodCombinationMapper.selectTakInFoodDetail(takeIn.getId());
        List<FoodCombination> newFoodCombination = new ArrayList<>();
        for(Map<String, Object> obj: combinationList){
            FoodCombination foodCombination = new FoodCombination();
            Food foodInfo = new Food();

            foodInfo.setFoodId((String)obj.get("food_id"));
            foodInfo.setFoodId((String)obj.get("name"));
            foodInfo.setLevel((int)obj.get("level"));
            foodInfo.setCalories((Float)obj.get("calories"));
            foodInfo.setPerPart((String)obj.get("per_part"));
            foodInfo.setPerPart((String)obj.get("intro"));
            foodInfo.setImg((String)obj.get("img"));

            foodCombination.setFoodInfo(foodInfo);
            foodCombination.setAmount((int)obj.get("amount"));
            newFoodCombination.add(foodCombination);
        }
        return newFoodCombination;
    }
}
