package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbSeller;

import java.util.List;

public interface TbSellerMapper {
    int deleteByPrimaryKey(String[] sellerIds);

    int insert(TbSeller record);

    int insertSelective(TbSeller record);

    TbSeller selectByPrimaryKey(String sellerId);

    List<TbSeller> selectByItems(TbSeller tbSeller);

    int updateByPrimaryKeySelective(TbSeller record);

    int updateByPrimaryKey(TbSeller record);
}