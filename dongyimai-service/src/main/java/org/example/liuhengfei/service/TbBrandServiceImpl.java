package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.mapper.TbBrandMapper;
import org.example.liuhengfei.pojo.TbBrand;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class TbBrandServiceImpl implements TbBrandService {

    @Resource
    private TbBrandMapper tbBrandMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbBrand> findAll() {
        return tbBrandMapper.selectByItems(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize, TbBrand tbBrand) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbBrand> page = (Page<TbBrand>) tbBrandMapper.selectByItems(tbBrand);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 返回列表选项
     */
    @Override
    public List<Map> selectOptions() {
        return tbBrandMapper.selectOptions();
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbBrand findOne(Long id) {
        return tbBrandMapper.selectByPrimaryKey(id);
    }

    /**
     * 增加
     */
    @Override
    public int add(TbBrand tbBrand) {
        return tbBrandMapper.insert(tbBrand);
    }


    /**
     * 修改
     */
    @Override
    public int update(TbBrand tbBrand) {
        return tbBrandMapper.updateByPrimaryKey(tbBrand);
    }

    /**
     * 批量删除
     */
    @Override
    public int delete(Long[] ids) {
        return tbBrandMapper.deleteByPrimaryKey(ids);
    }

}
