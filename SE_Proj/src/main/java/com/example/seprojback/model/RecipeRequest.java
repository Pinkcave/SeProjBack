package com.example.seprojback.model;

import com.example.seprojback.entity.Recipe;
import com.example.seprojback.entity.RecipeFoodCombination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {
    public RecipeRequest(Recipe recipe){
        this.recipeId = recipe.getRecipeId();
        this.isShow = recipe.getIsShow();
        this.state = recipe.getState();
        this.createTime = recipe.getCreateTime();
        this.name = recipe.getName();
        this.intro = recipe.getIntro();
        this.ownerId = recipe.getOwnerId();
        this.recipeUrl = recipe.getRecipeUrl();
        this.foodCombination = new ArrayList<FoodCombination>();
    }
    private String recipeId;
    private int isShow;
    private int state;
    private Date createTime;
    private String name;
    private String intro;
    private String ownerId;
    private String recipeUrl;
    private List<FoodCombination> foodCombination;
}
