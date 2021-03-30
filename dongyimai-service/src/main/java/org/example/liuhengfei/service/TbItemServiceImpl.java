package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.liuhengfei.mapper.TbItemMapper;
import org.example.liuhengfei.pojo.TbItem;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbItemServiceImpl implements TbItemService {

    @Resource
    private TbItemMapper tbItemMapper;

    @Override
    public List<TbItem> find(Long[] goodsIds) {
        return tbItemMapper.selectByGoodsId(goodsIds);
    }

    @Override
    public TbItem findOne(Long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }
}
