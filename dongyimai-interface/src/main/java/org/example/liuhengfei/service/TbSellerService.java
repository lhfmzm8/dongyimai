package org.example.liuhengfei.service;

import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.pojo.TbSeller;

import java.util.List;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface TbSellerService {

    /**
     * 返回全部列表
     *
     * @return
     */
    List<TbSeller> findAll();

    /**
     * 返回分页列表
     *
     * @return
     */
    PageResult findPage(int pageNum, int pageSize, TbSeller tbSeller);

    /**
     * 根据ID获取实体
     *
     * @param sellerId
     * @return
     */
    TbSeller findOne(String sellerId);

    /**
     * 增加
     */
    void add(TbSeller tbSeller);

    /**
     * 修改
     */
    void update(TbSeller tbSeller);

    /**
     * 批量删除
     *
     * @param sellerIds
     */
    void delete(String[] sellerIds);

}
