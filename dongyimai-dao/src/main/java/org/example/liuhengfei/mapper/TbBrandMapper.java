package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbBrand;

import java.util.List;
import java.util.Map;

public interface TbBrandMapper {
    int deleteByPrimaryKey(Long[] ids);

    int insert(TbBrand record);

    int insertSelective(TbBrand record);

    TbBrand selectByPrimaryKey(Long id);

    List<TbBrand> selectByItems(TbBrand tbBrand);

    List<Map> selectOptions();

    int updateByPrimaryKeySelective(TbBrand record);

    int updateByPrimaryKey(TbBrand record);
}