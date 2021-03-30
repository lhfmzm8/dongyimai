package org.example.liuhengfei.service;

import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.pojo.TbBrand;

import java.util.List;
import java.util.Map;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface TbBrandService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbBrand> findAll();

    /**
     * 返回分页列表
     *
     * @return
     */
    PageResult findPage(int pageNum, int pageSize, TbBrand tbBrand);

    /**
     * 返回列表选项
     */
    List<Map> selectOptions();

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    TbBrand findOne(Long id);

    /**
     * 增加
     */
    int add(TbBrand tbBrand);

    /**
     * 修改
     */
    int update(TbBrand tbBrand);

    /**
     * 批量删除
     *
     * @param ids
     */
    int delete(Long[] ids);

}
