package com.example.seprojback.service.impl;

import com.example.seprojback.model.RecipeRequest;
import com.example.seprojback.entity.Comment;
import com.example.seprojback.entity.Food;
import com.example.seprojback.mapper.AdminMapper;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.AdminService;
import com.example.seprojback.service.CommentService;
import com.example.seprojback.service.FoodService;
import com.example.seprojback.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private FoodService foodService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public ResponseResult modifyFood(Food food){
        return foodService.updateFood(food);
    }
    @Override
    public ResponseResult auditComment(String commentId,boolean result){
        if(result){
            Comment comment = commentService.getCommentDetail(commentId);
            comment.setState(1);
            return commentService.submitForApproval(comment);
        }
        else{
            return commentService.deleteComment(commentId);
        }
    }
    @Override
    public ResponseResult auditRecipe(String recipeId, boolean result){
        RecipeRequest recipeRequest = recipeService.getRecipeDetail(recipeId);
        if(result){
            recipeRequest.setState(2);
            recipeRequest.setIsShow(1);
        }
        else{
            recipeRequest.setState(0);
        }
        return recipeService.updateRecipe(recipeRequest);
    }
}
