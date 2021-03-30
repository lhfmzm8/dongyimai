package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.liuhengfei.mapper.TbItemCatMapper;
import org.example.liuhengfei.pojo.TbItemCat;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品类目服务实现层
 *
 * @author Administrator
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {

    @Resource
    private TbItemCatMapper tbItemCatMapper;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 获取所有
     *
     * @return
     */
    @Override
    public List<TbItemCat> findAll() {
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByItems(null);
        for(TbItemCat tbItemCat:tbItemCats){
            redisTemplate.boundHashOps("tbItemCats").put(tbItemCat.getName(),tbItemCat);
        }
        return tbItemCats;
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbItemCat findOne(Long id) {
        return tbItemCatMapper.selectByPrimaryKey(id);
    }

    /**
     * 增加、修改
     *
     * @param tbItemCat
     */
    @Override
    public void save(TbItemCat tbItemCat) {
        if (tbItemCat.getId() == null) {
            tbItemCatMapper.insertSelective(tbItemCat);
        } else {
            tbItemCatMapper.updateByPrimaryKeySelective(tbItemCat);
        }
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        TbItemCat tbItemCat = new TbItemCat();
        for (Long id : ids) {
            tbItemCat.setParentId(id);
            List<TbItemCat> childTbItemCats = tbItemCatMapper.selectByItems(tbItemCat);
            if (childTbItemCats.size() > 0) {
                Long[] childIds = new Long[childTbItemCats.size()];
                for (int i = 0; i < childTbItemCats.size(); i++) {
                    childIds[i] = childTbItemCats.get(i).getId();
                }
                delete(childIds);
            }
            tbItemCatMapper.deleteByPrimaryKey(new Long[]{id});
        }
    }

    /**
     * 根据父ID查询列表
     *
     * @param parentId
     * @return
     */
    @Override
    public List<TbItemCat> findByParentId(Long parentId) {
        TbItemCat tbItemCat = new TbItemCat();
        tbItemCat.setParentId(parentId);
        return tbItemCatMapper.selectByItems(tbItemCat);
    }

}
