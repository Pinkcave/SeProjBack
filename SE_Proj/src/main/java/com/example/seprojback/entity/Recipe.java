package com.example.seprojback.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.seprojback.model.RecipeRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("recipe")
public class Recipe {
    @TableId
    private String recipeId;
    private int isShow;
    private int state;
    private Date createTime;
    private String name;
    private String intro;
    private String ownerId;
    private String recipeUrl;
    public void SetRecipe(RecipeRequest recipeRequest){
        this.recipeId = recipeRequest.getRecipeId();
        this.isShow = recipeRequest.getIsShow();
        this.state = recipeRequest.getState();
        this.createTime = recipeRequest.getCreateTime();
        this.name = recipeRequest.getName();
        this.intro = recipeRequest.getIntro();
        this.ownerId = recipeRequest.getOwnerId();
        this.recipeUrl = recipeRequest.getRecipeUrl();
    }
}
