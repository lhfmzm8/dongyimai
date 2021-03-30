package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.mapper.*;
import org.example.liuhengfei.pojo.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class TbGoodsServiceImpl implements TbGoodsService {

    @Resource
    private TbGoodsMapper tbGoodsMapper;

    @Resource
    private TbGoodsDescMapper tbGoodsDescMapper;

    @Resource
    private TbItemMapper tbItemMapper;

    @Resource
    private TbBrandMapper tbBrandMapper;

    @Resource
    private TbItemCatMapper tbItemCatMapper;

    @Resource
    private TbSellerMapper tbSellerMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return tbGoodsMapper.selectByItems(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize, TbGoods tbGoods) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbGoods> page = (Page<TbGoods>) tbGoodsMapper.selectByItems(tbGoods);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbGoods findOne(Long id, String sellerId) {
        TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
        if (sellerId.equals(tbGoods.getSellerId())) {
            tbGoods.setTbGoodsDesc(tbGoodsDescMapper.selectByPrimaryKey(id));
            TbItem tbItem = new TbItem();
            tbItem.setGoodsId(id);
            List<TbItem> tbItems = tbItemMapper.selectByItems(tbItem);
            tbGoods.setSKUList(tbItems);
            return tbGoods;
        }
        return null;
    }

    @Override
    public TbGoods findOne(Long id) {
        TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
        tbGoods.setTbGoodsDesc(tbGoodsDescMapper.selectByPrimaryKey(id));
        TbItem tbItem = new TbItem();
        tbItem.setGoodsId(id);
        List<TbItem> tbItems = tbItemMapper.selectByItems(tbItem);
        tbGoods.setSKUList(tbItems);
        return tbGoods;
    }

    /**
     * 增加、修改
     */
    @Override
    public void save(TbGoods tbGoods, String sellerId) {
        tbGoods.setAuditStatus("0");
        if (tbGoods.getId() == null) {
            tbGoods.setSellerId(sellerId);
            tbGoodsMapper.insertSelective(tbGoods);
            TbGoodsDesc tbGoodsDesc = tbGoods.getTbGoodsDesc();
            tbGoodsDesc.setGoodsId(tbGoods.getId());
            tbGoodsDescMapper.insertSelective(tbGoodsDesc);
        } else {
            tbGoodsMapper.updateByPrimaryKeySelective(tbGoods);
            tbGoodsDescMapper.updateByPrimaryKeySelective(tbGoods.getTbGoodsDesc());
            tbItemMapper.deleteByGoodsId(tbGoods.getId());
        }
        saveSKUList(tbGoods);
    }

    //保存tb_item
    private void saveSKUList(TbGoods tbGoods) {
        if ("1".equals(tbGoods.getIsEnableSpec())) {
            for (TbItem tbItem : tbGoods.getSKUList()) {
                //标题
                String title = tbGoods.getGoodsName();
                Map<String, Object> specMap = JSON.parseObject(tbItem.getSpec());
                for (String key : specMap.keySet()) {
                    title += " " + specMap.get(key);
                }
                tbItem.setTitle(title);
                setItemValues(tbGoods, tbItem);
            }
        } else {
            TbItem tbItem = new TbItem();
            tbItem.setTitle(tbGoods.getGoodsName());
            tbItem.setPrice(tbGoods.getPrice());
            tbItem.setIsDefault("1");//是否默认
            tbItem.setNum(99999);//库存数量
            tbItem.setSpec("{}");
            setItemValues(tbGoods, tbItem);
        }
    }

    private void setItemValues(TbGoods tbGoods, TbItem tbItem) {
        tbItem.setGoodsId(tbGoods.getId());//商品SPU编号
        tbItem.setSellerId(tbGoods.getSellerId());//商家编号
        tbItem.setCategoryid(tbGoods.getCategory3Id());//商品分类编号（3级）
        tbItem.setCreateTime(new Date());//创建日期
        tbItem.setUpdateTime(new Date());//修改日期
        tbItem.setStatus(tbGoods.getAuditStatus());//状态
        //品牌名称
        TbBrand tbBrand = tbBrandMapper.selectByPrimaryKey(tbGoods.getBrandId());
        tbItem.setBrand(tbBrand.getName());
        //分类名称
        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
        tbItem.setCategory(tbItemCat.getName());
        //商家名称
        TbSeller tbSeller = tbSellerMapper.selectByPrimaryKey(tbGoods.getSellerId());
        tbItem.setSeller(tbSeller.getNickName());
        //图片地址（取spu的第一个图片）
        List<Map> imageList = JSON.parseArray(tbGoods.getTbGoodsDesc().getItemImages(), Map.class);
        if (imageList.size() > 0) {
            tbItem.setImage((String) imageList.get(0).get("url"));
        }
        tbItemMapper.insertSelective(tbItem);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        tbGoodsMapper.deleteByPrimaryKey(ids);
    }

    /**
     * 批量修改状态
     *
     * @param status
     * @return
     */
    @Override
    public void updateStatus(Long[] ids, String status) {
        for (Long id : ids) {
            TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
            tbGoods.setAuditStatus(status);
            tbGoodsMapper.updateByPrimaryKeySelective(tbGoods);
            tbItemMapper.updateByGoodsId(id, status);
        }
    }
}