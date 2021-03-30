package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbCities;

public interface TbCitiesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbCities record);

    int insertSelective(TbCities record);

    TbCities selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbCities record);

    int updateByPrimaryKey(TbCities record);
}