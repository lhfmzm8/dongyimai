package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbSpecificationOption;

import java.util.List;

public interface TbSpecificationOptionMapper {
    int deleteByPrimaryKey(Long[] ids);

    int deleteBySpecId(Long specId);

    int insert(TbSpecificationOption record);

    int insertSelective(TbSpecificationOption record);

    TbSpecificationOption selectByPrimaryKey(Long id);

    List<TbSpecificationOption> selectByItems(TbSpecificationOption tbSpecificationOption);

    int updateByPrimaryKeySelective(TbSpecificationOption record);

    int updateByPrimaryKey(TbSpecificationOption record);
}