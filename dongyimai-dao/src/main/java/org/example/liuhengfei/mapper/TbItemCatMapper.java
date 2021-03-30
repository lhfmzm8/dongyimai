package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbItemCat;

import java.util.List;

public interface TbItemCatMapper {
    int deleteByPrimaryKey(Long[] ids);

    int insert(TbItemCat record);

    int insertSelective(TbItemCat record);

    TbItemCat selectByPrimaryKey(Long id);

    List<TbItemCat> selectByItems(TbItemCat tbItemCat);

    int updateByPrimaryKeySelective(TbItemCat record);

    int updateByPrimaryKey(TbItemCat record);

}