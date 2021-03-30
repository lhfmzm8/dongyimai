package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbAddress;

import java.util.List;

public interface TbAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbAddress record);

    int insertSelective(TbAddress record);

    TbAddress selectByPrimaryKey(Long id);

    List<TbAddress> selectByItems(TbAddress tbAddress);

    int updateByPrimaryKeySelective(TbAddress record);

    int updateByPrimaryKey(TbAddress record);
}