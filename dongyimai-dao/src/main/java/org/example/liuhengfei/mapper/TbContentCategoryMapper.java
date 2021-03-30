package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbContentCategory;

import java.util.List;

public interface TbContentCategoryMapper {
    int deleteByPrimaryKey(Long[] ids);

    int insert(TbContentCategory record);

    int insertSelective(TbContentCategory record);

    TbContentCategory selectByPrimaryKey(Long id);

    List<TbContentCategory> selectByItems(TbContentCategory tbContentCategory);

    int updateByPrimaryKeySelective(TbContentCategory record);

    int updateByPrimaryKey(TbContentCategory record);
}