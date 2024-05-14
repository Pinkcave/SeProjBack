package com.example.seprojback.service;

import com.example.seprojback.model.RecipeRequest;
import com.example.seprojback.model.ResponseResult;

import java.util.List;

public interface RecipeService {
    List<RecipeRequest> getRecipes(String userId);
    List<RecipeRequest> getRecipesBatch(int offset,int size);
    RecipeRequest getRecipeDetail(String recipeId);
    ResponseResult updateRecipe(RecipeRequest recipeReq);
    ResponseResult deleteRecipe(String recipeId);
    ResponseResult addRecipe(RecipeRequest recipeReq);
    List<RecipeRequest> searchRecipes(String key);
    ResponseResult launchRecipe(String recipeId);
    ResponseResult recallRecipes(String recipeId);
    List<RecipeRequest> getLaunchedRecipes();
    List<RecipeRequest> getUnauditedRecipes();


}
