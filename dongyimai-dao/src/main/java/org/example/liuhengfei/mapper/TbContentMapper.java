package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbContent;

import java.util.List;

public interface TbContentMapper {
    int deleteByPrimaryKey(Long[] ids);

    int insert(TbContent record);

    int insertSelective(TbContent record);

    TbContent selectByPrimaryKey(Long id);

    List<TbContent> selectByItems(TbContent tbContent);

    int updateByPrimaryKeySelective(TbContent record);

    int updateByPrimaryKey(TbContent record);
}