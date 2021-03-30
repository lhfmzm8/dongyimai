package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbPayLog;

public interface TbPayLogMapper {
    int deleteByPrimaryKey(String outTradeNo);

    int insert(TbPayLog record);

    int insertSelective(TbPayLog record);

    TbPayLog selectByPrimaryKey(String outTradeNo);

    int updateByPrimaryKeySelective(TbPayLog record);

    int updateByPrimaryKey(TbPayLog record);
}