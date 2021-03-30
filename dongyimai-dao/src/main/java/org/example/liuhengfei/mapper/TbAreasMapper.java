package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbAreas;

public interface TbAreasMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbAreas record);

    int insertSelective(TbAreas record);

    TbAreas selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAreas record);

    int updateByPrimaryKey(TbAreas record);
}