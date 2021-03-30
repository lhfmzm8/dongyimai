package org.example.liuhengfei.service;

import org.example.liuhengfei.pojo.TbItem;

import java.util.List;

public interface TbItemService {

    List<TbItem> find(Long[] goodsIds);

    TbItem findOne(Long id);

}
