package org.example.liuhengfei.mapper;

import org.example.liuhengfei.pojo.TbTypeTemplate;

import java.util.List;
import java.util.Map;

public interface TbTypeTemplateMapper {
    int deleteByPrimaryKey(Long[] ids);

    int insert(TbTypeTemplate record);

    int insertSelective(TbTypeTemplate record);

    TbTypeTemplate selectByPrimaryKey(Long id);

    List<TbTypeTemplate> selectByItems(TbTypeTemplate tbTypeTemplate);

    List<Map> selectOptionList();

    int updateByPrimaryKeySelective(TbTypeTemplate record);

    int updateByPrimaryKey(TbTypeTemplate record);
}