package org.example.liuhengfei.service;

import org.example.liuhengfei.pojo.TbItemCat;

import java.util.List;

/**
 * 商品类目服务层接口
 *
 * @author Administrator
 */
public interface TbItemCatService {

    /**
     * 获取所有
     *
     * @return
     */
    List<TbItemCat> findAll();

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    TbItemCat findOne(Long id);

    /**
     * 增加、修改
     *
     * @param tbItemCat
     */
    void save(TbItemCat tbItemCat);

    /**
     * 批量删除
     *
     * @param ids
     */
    void delete(Long[] ids);

    /**
     * 根据父ID查询列表
     *
     * @param parentId
     * @return
     */
    List<TbItemCat> findByParentId(Long parentId);

}
