package com.example.seprojback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seprojback.entity.Food;
import com.example.seprojback.entity.Recipe;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecipeMapper extends BaseMapper<Recipe> {
}
