package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbSeckillGoods;

public interface TbSeckillGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbSeckillGoods record);

    int insertSelective(TbSeckillGoods record);

    TbSeckillGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbSeckillGoods record);

    int updateByPrimaryKey(TbSeckillGoods record);
}