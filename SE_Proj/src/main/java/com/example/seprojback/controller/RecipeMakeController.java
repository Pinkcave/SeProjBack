package com.example.seprojback.controller;

import com.example.seprojback.model.RecipeRequest;
import com.example.seprojback.entity.Food;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.FoodService;
import com.example.seprojback.service.RecipeService;
import com.example.seprojback.utils.TokenOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeMakeController {
    @Autowired
    public RecipeService recipeService;
    @Autowired
    public FoodService foodService;
    @PostMapping("/recipes")
    public ResponseResult makeRecipe(@RequestBody RecipeRequest recipeRequest){
        recipeRequest.setOwnerId(TokenOperation.getUserIdFromToken());
        return recipeService.addRecipe(recipeRequest);
    }

    @GetMapping("/recipes")
    public List<RecipeRequest> getRecipeList(){
        String userId = TokenOperation.getUserIdFromToken();
        return recipeService.getRecipes(userId);
    }

    @GetMapping("/recipe/detail")
    public RecipeRequest getDetailedRecipe(@RequestParam String recipeId){
        return recipeService.getRecipeDetail(recipeId);
    }

    @PutMapping("/recipes")
    public ResponseResult modifyRecipe(@RequestBody RecipeRequest recipeRequest){
        recipeRequest.setOwnerId(TokenOperation.getUserIdFromToken());
        return recipeService.updateRecipe(recipeRequest);
    }

    @DeleteMapping("/recipes")
    public ResponseResult deleteRecipe(@RequestParam String recipeId){
        return recipeService.deleteRecipe(recipeId);
    }

    @PostMapping("/recipes/launch")
    public ResponseResult launchRecipe(@RequestParam String recipeId){
        return recipeService.launchRecipe(recipeId);
    }

    @PostMapping("/recipes/recall")
    public ResponseResult recallRecipe(@RequestParam String recipeId) {
        return recipeService.recallRecipes(recipeId);
    }

    @GetMapping("food")
    public List<Food> getFood(){
        return foodService.getFoodList();
    }
}
