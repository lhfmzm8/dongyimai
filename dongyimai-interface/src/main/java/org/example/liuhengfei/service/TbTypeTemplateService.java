package org.example.liuhengfei.service;

import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.pojo.TbTypeTemplate;

import java.util.List;
import java.util.Map;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface TbTypeTemplateService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbTypeTemplate> findAll();

    /**
     * 返回分页列表
     *
     * @return
     */
    PageResult findPage(int pageNum, int pageSize, TbTypeTemplate tbTypeTemplate);

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    TbTypeTemplate findOne(Long id);

    /**
     * 返回模板选项
     *
     * @return
     *
     */
    List<Map> selectOptionList();

    List<Map> selectOptionList(Long id);

    /**
     * 增加、修改
     */
    void save(TbTypeTemplate tbTypeTemplate);

    /**
     * 批量删除
     *
     * @param ids
     */
    void delete(Long[] ids);

}
