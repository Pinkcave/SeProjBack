package com.example.seprojback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.seprojback.entity.Comment;
import com.example.seprojback.mapper.CommentMapper;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public ResponseResult saveComment(Comment comment){
        long timestamp = System.currentTimeMillis();
        comment.setCommentId(comment.getUserId()+'-'+comment.getRecipeId()+'-'+timestamp);
        comment.setTime(new Date(timestamp));
        comment.setState(0);
        if(commentMapper.insert(comment)<=0){
            throw new RuntimeException("评论保存失败");
        }
        return new ResponseResult(200,"评论保存成功");
    }
    @Override
    public List<Comment> getComments(String recipeId){
        if(recipeId==null){
            return commentMapper.selectList(null);
        }
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRecipeId,recipeId).eq(Comment::getState,1);
        return commentMapper.selectList(queryWrapper);
    }
    @Override
    public Comment getCommentDetail(String commentId){
        return commentMapper.selectById(commentId);
    }
    @Override
    public ResponseResult submitForApproval(Comment comment){
        LambdaUpdateWrapper<Comment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Comment::getState,comment.getState()).eq(Comment::getCommentId,comment.getCommentId());
        if(commentMapper.update(updateWrapper)<=0){
            throw new RuntimeException("发布失败");
        }
        return new ResponseResult(200,"发布成功");
    }
    @Override
    public List<Comment> getUnAuditedComment(){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getState,0);
        return commentMapper.selectList(queryWrapper);
    }
    @Override
    public ResponseResult deleteComment(String commentId){
        if(commentMapper.deleteById(commentId)<=0){
            throw new RuntimeException("评论不存在");
        }

        return new ResponseResult(200,"评论删除成功");
    }
}
