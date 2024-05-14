package com.example.seprojback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class TakeInRequest {
    private int cate;
    private List<RawFoodCombination> foodCombine;
    public TakeInRequest(int cate, List<RawFoodCombination> list){
        this.cate = cate;
        this.foodCombine = new ArrayList<RawFoodCombination>(list);
    }
}
