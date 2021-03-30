package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbSpecification;

import java.util.List;
import java.util.Map;

public interface TbSpecificationMapper {
    int deleteByPrimaryKey(Long[] ids);

    int insert(TbSpecification record);

    int insertSelective(TbSpecification record);

    TbSpecification selectByPrimaryKey(Long id);

    List<TbSpecification> selectByItems(TbSpecification tbSpecification);

    List<Map> selectOptions();

    int updateByPrimaryKeySelective(TbSpecification record);

    int updateByPrimaryKey(TbSpecification record);
}