package org.example.liuhengfei.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.liuhengfei.pojo.TbItem;

import java.util.List;

public interface TbItemMapper {
    int deleteByPrimaryKey(Long[] ids);

    int deleteByGoodsId(Long goodsId);

    int insert(TbItem record);

    int insertSelective(TbItem record);

    TbItem selectByPrimaryKey(Long ids);

    List<TbItem> selectByItems(TbItem tbItem);

    List<TbItem> selectByGoodsId(Long[] goodIds);

    int updateByPrimaryKeySelective(TbItem record);

    int updateByPrimaryKey(TbItem record);

    int updateByGoodsId(@Param("goodsId") Long goodsId, @Param("status") String status);
}