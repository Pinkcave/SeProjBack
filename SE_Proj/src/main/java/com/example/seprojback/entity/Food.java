package com.example.seprojback.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("food")
public class Food {
    @TableId
    private String foodId;
    private String name;
    private int level;
    private float calories;
    private String perPart;
    private String intro;
    private String img;
}
