package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.mapper.TbSpecificationOptionMapper;
import org.example.liuhengfei.mapper.TbTypeTemplateMapper;
import org.example.liuhengfei.pojo.TbSpecificationOption;
import org.example.liuhengfei.pojo.TbTypeTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class TbTypeTemplateServiceImpl implements TbTypeTemplateService {

    @Resource
    private TbTypeTemplateMapper tbTypeTemplateMapper;

    @Resource
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     */
    @Override
    public List<TbTypeTemplate> findAll() {
        List<TbTypeTemplate> tbTypeTemplates = tbTypeTemplateMapper.selectByItems(null);
        for (TbTypeTemplate tbTypeTemplate : tbTypeTemplates) {
            //缓存品牌
            List<Map> tbBrands = JSON.parseArray(tbTypeTemplate.getBrandIds(), Map.class);
            redisTemplate.boundHashOps("tbBrands").put(tbTypeTemplate.getId(), tbBrands);
            //缓存规格
            List<Map> specs = JSON.parseArray(tbTypeTemplate.getSpecIds(), Map.class);
            for (Map map : specs) {
                TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
                tbSpecificationOption.setSpecId(Long.parseLong(map.get("id") + ""));
                List<TbSpecificationOption> options = tbSpecificationOptionMapper.selectByItems(tbSpecificationOption);
                map.put("options", options);
            }
            redisTemplate.boundHashOps("specs").put(tbTypeTemplate.getId(), specs);
        }
        return tbTypeTemplates;
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize, TbTypeTemplate tbTypeTemplate) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) tbTypeTemplateMapper.selectByItems(tbTypeTemplate);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbTypeTemplate findOne(Long id) {
        return tbTypeTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 返回列表选项
     *
     */
    @Override
    public List<Map> selectOptionList() {
        return tbTypeTemplateMapper.selectOptionList();
    }

    @Override
    public List<Map> selectOptionList(Long id) {
        List<Map> specs = JSON.parseArray(tbTypeTemplateMapper.selectByPrimaryKey(id).getSpecIds(), Map.class);
        for (Map map : specs) {
            TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
            tbSpecificationOption.setSpecId(Long.parseLong(map.get("id") + ""));
            List<TbSpecificationOption> options = tbSpecificationOptionMapper.selectByItems(tbSpecificationOption);
            map.put("options", options);
        }
        return specs;
    }

    /**
     * 增加、修改
     */
    @Override
    public void save(TbTypeTemplate tbTypeTemplate) {
        tbTypeTemplateMapper.insert(tbTypeTemplate);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        tbTypeTemplateMapper.deleteByPrimaryKey(ids);
    }

}
