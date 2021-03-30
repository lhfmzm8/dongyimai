package org.example.liuhengfei.service;

import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.pojo.TbContent;

import java.util.List;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface TbContentService {
int x=0;
    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbContent> findAll();

    /**
     * 返回分页列表
     *
     * @return
     */
    PageResult findPage(int pageNum, int pageSize, TbContent tbContent);

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    TbContent findOne(Long id);

    /**
     * 根据广告位ID查询列表
     *
     * @param categoryId
     * @return
     */
    List<TbContent> findByCategoryId(Long categoryId);

    /**
     * 增加
     */
    void add(TbContent tbContent);

    /**
     * 修改
     */
    void update(TbContent tbContent);

    /**
     * 批量删除
     *
     * @param ids
     */
    void delete(Long[] ids);

}
