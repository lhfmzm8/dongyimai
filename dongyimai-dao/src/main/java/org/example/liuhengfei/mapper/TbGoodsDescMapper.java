package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbGoodsDesc;

import java.util.List;

public interface TbGoodsDescMapper {
    int deleteByPrimaryKey(Long[] goodsIds);

    int insert(TbGoodsDesc record);

    int insertSelective(TbGoodsDesc record);

    TbGoodsDesc selectByPrimaryKey(Long goodsId);

    List<TbGoodsDesc> selectByItems(TbGoodsDesc tbGoodsDesc);

    int updateByPrimaryKeySelective(TbGoodsDesc record);

    int updateByPrimaryKey(TbGoodsDesc record);
}