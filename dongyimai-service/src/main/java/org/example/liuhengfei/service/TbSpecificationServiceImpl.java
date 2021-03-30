package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.mapper.TbSpecificationMapper;
import org.example.liuhengfei.mapper.TbSpecificationOptionMapper;
import org.example.liuhengfei.pojo.TbSpecification;
import org.example.liuhengfei.pojo.TbSpecificationOption;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class TbSpecificationServiceImpl implements TbSpecificationService {

    @Resource
    private TbSpecificationMapper tbSpecificationMapper;

    @Resource
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbSpecification> findAll() {
        return tbSpecificationMapper.selectByItems(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize, TbSpecification tbSpecification) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbSpecification> page = (Page<TbSpecification>) tbSpecificationMapper.selectByItems(tbSpecification);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 返回列表选项
     */
    @Override
    public List<Map> selectOptions() {
        return tbSpecificationMapper.selectOptions();
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbSpecification findOne(Long id) {
        TbSpecification tbSpecification = tbSpecificationMapper.selectByPrimaryKey(id);
        TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
        tbSpecificationOption.setSpecId(id);
        List<TbSpecificationOption> optionList = tbSpecificationOptionMapper.selectByItems(tbSpecificationOption);
        tbSpecification.setOptionList(optionList);
        return tbSpecification;
    }

    /**
     * 增加、修改
     */
    @Override
    public void save(TbSpecification tbSpecification) {
        Long tbSpecificationId = tbSpecification.getId();
        if (tbSpecificationId == null) {
            tbSpecificationMapper.insertSelective(tbSpecification);
        } else {
            tbSpecificationMapper.updateByPrimaryKey(tbSpecification);
            tbSpecificationOptionMapper.deleteBySpecId(tbSpecificationId);
        }
        List<TbSpecificationOption> optionList = tbSpecification.getOptionList();
        for (TbSpecificationOption tbSpecificationOption : optionList) {
            tbSpecificationOption.setSpecId(tbSpecificationId);
            tbSpecificationOptionMapper.insertSelective(tbSpecificationOption);
        }
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id:ids) {
            tbSpecificationOptionMapper.deleteBySpecId(id);
        }
        tbSpecificationMapper.deleteByPrimaryKey(ids);
    }

}
