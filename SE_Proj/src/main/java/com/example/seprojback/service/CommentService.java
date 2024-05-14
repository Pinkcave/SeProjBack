package com.example.seprojback.service;

import com.example.seprojback.entity.Comment;
import com.example.seprojback.model.ResponseResult;

import java.util.List;

public interface CommentService {
    ResponseResult saveComment(Comment comment);
    List<Comment> getComments(String recipeId);
    Comment getCommentDetail(String commentId);
    ResponseResult submitForApproval(Comment commentId);

    List<Comment> getUnAuditedComment();
    ResponseResult deleteComment(String commentId);
}
