package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbOrder;

public interface TbOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(TbOrder record);

    int insertSelective(TbOrder record);

    TbOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(TbOrder record);

    int updateByPrimaryKey(TbOrder record);
}