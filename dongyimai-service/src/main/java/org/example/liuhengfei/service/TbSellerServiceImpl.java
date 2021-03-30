package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.mapper.TbSellerMapper;
import org.example.liuhengfei.pojo.TbSeller;

import javax.annotation.Resource;
import java.util.List;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class TbSellerServiceImpl implements TbSellerService {

    @Resource
    private TbSellerMapper tbSellerMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbSeller> findAll() {
        return tbSellerMapper.selectByItems(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize, TbSeller tbSeller) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbSeller> page = (Page<TbSeller>) tbSellerMapper.selectByItems(tbSeller);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据ID获取实体
     *
     * @param sellerId
     * @return
     */
    @Override
    public TbSeller findOne(String sellerId) {
        return tbSellerMapper.selectByPrimaryKey(sellerId);
    }

    /**
     * 增加
     */
    @Override
    public void add(TbSeller tbSeller) {
        tbSellerMapper.insertSelective(tbSeller);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbSeller tbSeller) {
        tbSellerMapper.updateByPrimaryKeySelective(tbSeller);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(String[] sellerIds) {
        tbSellerMapper.deleteByPrimaryKey(sellerIds);
    }

}
