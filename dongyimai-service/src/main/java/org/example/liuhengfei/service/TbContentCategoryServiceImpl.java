package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.mapper.TbContentCategoryMapper;
import org.example.liuhengfei.pojo.TbContentCategory;

import javax.annotation.Resource;
import java.util.List;

/**
 * 内容分类服务实现层
 *
 * @author Administrator
 */
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

    @Resource
    private TbContentCategoryMapper tbContentCategoryMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbContentCategory> findAll() {
        return tbContentCategoryMapper.selectByItems(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize, TbContentCategory tbContentCategory) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbContentCategory> page = (Page<TbContentCategory>) tbContentCategoryMapper.selectByItems(tbContentCategory);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbContentCategory findOne(Long id) {
        return tbContentCategoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 增加
     */
    @Override
    public int add(TbContentCategory tbContentCategory) {
        return tbContentCategoryMapper.insert(tbContentCategory);
    }


    /**
     * 修改
     */
    @Override
    public int update(TbContentCategory tbContentCategory) {
        return tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
    }

    /**
     * 批量删除
     */
    @Override
    public int delete(Long[] ids) {
        return tbContentCategoryMapper.deleteByPrimaryKey(ids);
    }

}
