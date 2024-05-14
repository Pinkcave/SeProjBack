package com.example.seprojback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawFoodCombination {
    private int amount;
    private String foodId;
    private String name;
    private int level;
    private float calories;
    private String perPart;
    private String intro;
    private String img;
}
