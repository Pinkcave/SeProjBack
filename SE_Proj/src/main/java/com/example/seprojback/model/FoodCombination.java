package com.example.seprojback.model;

import com.example.seprojback.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodCombination {
    private Food foodInfo;
    private int amount;
}
