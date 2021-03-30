package org.example.liuhengfei.service;

import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.pojo.TbGoods;

import java.util.List;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface TbGoodsService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbGoods> findAll();

    /**
     * 返回分页列表
     *
     * @return
     */
    PageResult findPage(int pageNum, int pageSize, TbGoods tbGoods);

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    TbGoods findOne(Long id);

    TbGoods findOne(Long id, String sellerId);

    /**
     * 增加、修改
     */
    void save(TbGoods tbGoods, String sellerId);

    /**
     * 批量删除
     *
     * @param ids
     */
    void delete(Long[] ids);

    void updateStatus(Long[] ids, String status);
}
