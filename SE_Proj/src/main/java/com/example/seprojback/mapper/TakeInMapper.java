package com.example.seprojback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seprojback.entity.Takein;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface TakeInMapper extends BaseMapper<Takein> {
}
