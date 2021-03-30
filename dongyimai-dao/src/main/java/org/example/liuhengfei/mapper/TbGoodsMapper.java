package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbBrand;
import org.example.liuhengfei.pojo.TbGoods;

import java.util.List;

public interface TbGoodsMapper {
    int deleteByPrimaryKey(Long[] ids);

    int insert(TbGoods record);

    int insertSelective(TbGoods record);

    TbGoods selectByPrimaryKey(Long id);

    List<TbGoods> selectByItems(TbGoods tbGoods);

    int updateByPrimaryKeySelective(TbGoods record);

    int updateByPrimaryKey(TbGoods record);
}