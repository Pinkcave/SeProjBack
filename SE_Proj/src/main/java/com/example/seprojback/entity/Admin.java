package com.example.seprojback.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("admin")
public class Admin {
    @TableId
    private String adminId;
    private  String name;
    private String mailBox;
    private String gender;
    private String pwd;
    private String phoneNumber;
    private int level;
}
