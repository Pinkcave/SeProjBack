package com.example.seprojback.controller;

import com.example.seprojback.model.RecipeRequest;
import com.example.seprojback.entity.Comment;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.AdminService;
import com.example.seprojback.service.CommentService;
import com.example.seprojback.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class AuditController {
    @Autowired
    AdminService adminService;
    @Autowired
    CommentService commentService;
    @Autowired
    RecipeService recipeService;
    @GetMapping("/audit/comments")
    public List<Comment> getComments(){
        return commentService.getUnAuditedComment();
    }

    @PostMapping("/audit/comments")
    public ResponseResult auditComment(@RequestParam String commentId, @RequestParam boolean result){
        return adminService.auditComment(commentId,result);
    }

    @GetMapping("/audit/recipes")
    public List<RecipeRequest> getRecipes(){
        return recipeService.getUnauditedRecipes();
    }

    @PostMapping("/audit/recipes")
    public ResponseResult auditRecipe(@RequestParam String recipeId, @RequestParam boolean result){
        return adminService.auditRecipe(recipeId,result);
    }
}
