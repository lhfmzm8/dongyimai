package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbOrderItem;

public interface TbOrderItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbOrderItem record);

    int insertSelective(TbOrderItem record);

    TbOrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbOrderItem record);

    int updateByPrimaryKey(TbOrderItem record);
}