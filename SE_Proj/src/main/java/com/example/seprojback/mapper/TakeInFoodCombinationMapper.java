package com.example.seprojback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seprojback.entity.TakeinFoodCombination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TakeInFoodCombinationMapper extends BaseMapper<TakeinFoodCombination> {
    @Select("SELECT f.name, f.food_id, f.level, f.calories, f.per_part, f.intro, f.img, tfc.amount " +
            "from takein_food_combination tfc left join food f on tfc.food_id=f.food_id " +
            "where tfc.takein_id=#{takeInId}")
    List<Map<String, Object>> selectTakInFoodDetail(String takeInId);
}
