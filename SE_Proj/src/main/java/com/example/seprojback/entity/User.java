package com.example.seprojback.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId
    private String userId;
    private String name;
    private String pwd;
    private String mailBox;
    private String gender;
    private String phoneNumber;
    private String intro;
}
