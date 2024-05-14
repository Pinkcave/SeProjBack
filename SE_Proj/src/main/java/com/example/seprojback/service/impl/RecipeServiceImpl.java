package com.example.seprojback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.seprojback.entity.Food;
import com.example.seprojback.mapper.FoodMapper;
import com.example.seprojback.model.FoodCombination;
import com.example.seprojback.model.RecipeRequest;
import com.example.seprojback.entity.Recipe;
import com.example.seprojback.entity.RecipeFoodCombination;
import com.example.seprojback.mapper.RecipeFoodCombinationMapper;
import com.example.seprojback.mapper.RecipeMapper;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private RecipeFoodCombinationMapper recipeFoodCombinationMapper;
    @Autowired
    private  FoodMapper foodMapper;

    public List<RecipeRequest> RecipesToRecipeRequests(List<Recipe> recipes){
        List<RecipeRequest> recipeRequests = new ArrayList<>();
        recipes.forEach(recipe->{
            RecipeRequest recipeRequest = new RecipeRequest(recipe);
            LambdaQueryWrapper<RecipeFoodCombination> foodComWrapper = new LambdaQueryWrapper<>();
            foodComWrapper.eq(RecipeFoodCombination::getRecipeId,recipe.getRecipeId());
            List<RecipeFoodCombination> recipeFoodCombinationList = recipeFoodCombinationMapper.selectList(foodComWrapper);
            List<FoodCombination> foodCombinationList = new ArrayList<>();
            recipeFoodCombinationList.forEach(foodCom->{
                Food food = foodMapper.selectById(foodCom.getFoodId());
                foodCombinationList.add(new FoodCombination(food,foodCom.getAmount()));
            });
            recipeRequest.setFoodCombination(foodCombinationList);
            recipeRequests.add(recipeRequest);
        });
        return recipeRequests;
    }
    @Override
    public List<RecipeRequest> getRecipes(String userId){
        LambdaQueryWrapper<Recipe> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Recipe::getOwnerId,userId);
        List<Recipe> recipes = recipeMapper.selectList(queryWrapper);
        if(recipes==null){
            throw new RuntimeException("用户不存在或还未创建食谱");
        }
        return RecipesToRecipeRequests(recipes);
    }
    @Override
    public List<RecipeRequest> getRecipesBatch(int offset,int size){
        if(Objects.isNull(offset)){
            offset=0;
        }

        if(Objects.isNull(size)){
            size=10;
        }

        LambdaQueryWrapper<Recipe> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Recipe::getCreateTime);
        queryWrapper.last(String.format("limit %d,%d",offset,offset+size));
        return RecipesToRecipeRequests(recipeMapper.selectList(queryWrapper));
    }
    @Override
    public RecipeRequest getRecipeDetail(String recipeId){
        Recipe recipe = recipeMapper.selectById(recipeId);
        if(recipe==null){
            throw new RuntimeException("食谱不存在");
        }
//        RecipeRequest recipeRequest = new RecipeRequest(recipe);
//        LambdaQueryWrapper<RecipeFoodCombination> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(RecipeFoodCombination::getRecipeId,recipe.getRecipeId());
//        recipeRequest.setFoodCombination(recipeFoodCombinationMapper.selectList(queryWrapper));
//        return recipeRequest;
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        return RecipesToRecipeRequests(recipes).get(0);
    }
    @Override
    public ResponseResult updateRecipe(RecipeRequest recipeReq){
        Recipe oldRecipe = recipeMapper.selectById(recipeReq.getRecipeId());
        Recipe recipe = new Recipe();
        recipe.SetRecipe(recipeReq);
        if(recipeMapper.updateById(recipe)<=0){
            throw new RuntimeException("食谱不存在");
        }
        else{
            LambdaQueryWrapper<RecipeFoodCombination> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RecipeFoodCombination::getRecipeId,recipe.getRecipeId());
            recipeFoodCombinationMapper.delete(queryWrapper);
            recipeReq.getFoodCombination().forEach(foodCom->{
                RecipeFoodCombination recipeFoodCom = new RecipeFoodCombination(recipe.getRecipeId(), foodCom.getFoodInfo().getFoodId(),foodCom.getAmount());
                if(recipeFoodCombinationMapper.insert(recipeFoodCom)<=0){
                    recipeFoodCombinationMapper.delete(queryWrapper);
                    recipeMapper.updateById(oldRecipe);
                    throw new RuntimeException("更新食谱失败");
                }
            });
            return new ResponseResult(200,"更新食谱成功");
        }
    }
    @Override
    public ResponseResult deleteRecipe(String recipeId){
        LambdaQueryWrapper<RecipeFoodCombination> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RecipeFoodCombination::getRecipeId,recipeId);
        if(recipeFoodCombinationMapper.delete(queryWrapper)<=0){
            throw new RuntimeException("食谱不存在或删除过程出错");
        }
        else{
            if(recipeMapper.deleteById(recipeId)<=0){
                throw new RuntimeException("食谱不存在");
            }
            else{
                return new ResponseResult(200,"食谱删除成功");
            }
        }
    }
    @Override
    public ResponseResult addRecipe(RecipeRequest recipeReq){
        //init recipeRequest
        long timestamp = System.currentTimeMillis();
        recipeReq.setRecipeId(recipeReq.getOwnerId()+'-'+timestamp);
        recipeReq.setCreateTime(new Date(timestamp));
        recipeReq.setIsShow(0);
        recipeReq.setState(0);
        if(recipeReq.getRecipeUrl().equals("\0")){
            recipeReq.setRecipeUrl("http://localhost:8080/pics/default.png");
        }
        //insert
        Recipe recipe = new Recipe();
        recipe.SetRecipe(recipeReq);
        if(recipeMapper.insert(recipe)<=0){
            throw new RuntimeException("插入过程内部出错");
        }
        else{
            recipeReq.getFoodCombination().forEach(foodCom->{
                RecipeFoodCombination recipeFoodCom = new RecipeFoodCombination(recipe.getRecipeId(), foodCom.getFoodInfo().getFoodId(),foodCom.getAmount());
                if(recipeFoodCombinationMapper.insert(recipeFoodCom)<=0){
                    recipeMapper.deleteById(recipe);
                    throw new RuntimeException("插入过程内部出错");
                }
            });
            return new ResponseResult(200,"食谱插入成功");
        }
    }
    @Override
    public List<RecipeRequest> searchRecipes(String key){
        LambdaQueryWrapper<Recipe> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Recipe::getName,key);
        List<Recipe> recipes = recipeMapper.selectList(queryWrapper);
        if(recipes==null||recipes.isEmpty()){
            return new ArrayList<RecipeRequest>();
        }
        return RecipesToRecipeRequests(recipes);
    }
    @Override
    public ResponseResult launchRecipe(String recipeId){
//        Recipe recipe = recipeMapper.selectById(recipeId);
//        if(recipe==null){
//            throw new RuntimeException("食谱不存在");
//        }

//        recipe.setState(1);
        LambdaUpdateWrapper<Recipe> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Recipe::getState,1).eq(Recipe::getRecipeId,recipeId);
        if(recipeMapper.update(updateWrapper)<=0){
            throw new RuntimeException("食谱不存在");
        }
        return new ResponseResult(200,"食谱发布成功");
    }
    @Override
    public ResponseResult recallRecipes(String recipeId){
        Recipe recipe = recipeMapper.selectById(recipeId);
        if(recipe==null){
            throw new RuntimeException("食谱不存在");
        }

//        recipe.setState(0);
//        recipe.setIsShow(0);
        LambdaUpdateWrapper<Recipe> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Recipe::getState,0).set(Recipe::getIsShow,0).eq(Recipe::getRecipeId,recipe.getRecipeId());
        if(recipeMapper.update(updateWrapper)<=0){
            throw new RuntimeException("撤回过程出错");
        }
        return new ResponseResult(200,"食谱撤回成功");
    }
    @Override
    public List<RecipeRequest> getLaunchedRecipes(){
        LambdaQueryWrapper<Recipe> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Recipe::getIsShow,1);
        List<Recipe> recipes = recipeMapper.selectList(queryWrapper);
        if(recipes==null){
            throw new RuntimeException("没有发布食谱");
        }
        return RecipesToRecipeRequests(recipes);
    }
    @Override
    public List<RecipeRequest> getUnauditedRecipes(){
        LambdaQueryWrapper<Recipe> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Recipe::getIsShow,0).eq(Recipe::getState,1);
        List<Recipe> recipes = recipeMapper.selectList(queryWrapper);
        if(recipes==null){
            throw new RuntimeException("没有待审核食谱");
        }
        return RecipesToRecipeRequests(recipes);
    }
}
