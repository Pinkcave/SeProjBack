package com.example.seprojback.controller;

import com.example.seprojback.entity.Comment;
import com.example.seprojback.model.RecipeRequest;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.CommentService;
import com.example.seprojback.service.RecipeService;
import com.example.seprojback.utils.TokenOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.List;

@RestController
public class ShareAndCommentController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/share")
    public List<RecipeRequest> getRecipeBatch(@RequestParam int offset,@RequestParam int size){
        return recipeService.getRecipesBatch(offset,size);
    }

    @PostMapping("/comment")
    public ResponseResult commentRecipe(@RequestBody Comment comment){
        comment.setUserId(TokenOperation.getUserIdFromToken());
        return commentService.saveComment(comment);
    }

    @GetMapping("/comment")
    public List<Comment> getRecipeComments(@RequestParam String recipeId){
        return commentService.getComments(recipeId);
    }
}
