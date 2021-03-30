package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.mapper.TbContentMapper;
import org.example.liuhengfei.pojo.TbContent;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Resource
    private TbContentMapper tbContentMapper;

    @Resource
    private RedisTemplate redisTemplate;

    public static void main(String[] args) {

    }

    /**
     * 查询全部
     */
    @Override
    public List<TbContent> findAll() {
        return tbContentMapper.selectByItems(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize, TbContent tbContent) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbContent> page = (Page<TbContent>) tbContentMapper.selectByItems(tbContent);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbContent findOne(Long id) {
        return tbContentMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据广告位ID查询列表
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<TbContent> findByCategoryId(Long categoryId) {
        List<TbContent> tbContents = (List<TbContent>) redisTemplate.boundHashOps("tbContent").get(categoryId);
        if (tbContents == null) {
            TbContent tbContent = new TbContent();
            tbContent.setCategoryId(categoryId);
            tbContent.setStatus("1");
            tbContents = tbContentMapper.selectByItems(tbContent);
            redisTemplate.boundHashOps("tbContent").put(categoryId, tbContents);
        }
        return tbContents;
    }

    /**
     * 增加
     */
    @Override
    public void add(TbContent tbContent) {
        tbContentMapper.insert(tbContent);
        redisTemplate.boundHashOps("tbContent").delete(tbContent.getCategoryId());
    }


    /**
     * 修改
     */
    @Override
    public void update(TbContent tbContent) {
        Long categoryId = tbContentMapper.selectByPrimaryKey(tbContent.getId()).getCategoryId();
        redisTemplate.boundHashOps("tbContent").delete(categoryId);
        redisTemplate.boundHashOps("tbContent").delete(tbContent.getCategoryId());
        tbContentMapper.updateByPrimaryKey(tbContent);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            redisTemplate.boundHashOps("tbContent").delete(tbContentMapper.selectByPrimaryKey(id).getCategoryId());
        }
        tbContentMapper.deleteByPrimaryKey(ids);
    }

}
