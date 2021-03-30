package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.pojo.TbGoods;
import org.example.liuhengfei.service.TbGoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("TbGoodsControllerShop")
public class TbGoodsControllerShop {

    @Reference(timeout = 60000)
    private TbGoodsService tbGoodsService;

    /**
     * 分页查询
     *
     * @param tbGoods
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(int pageNum, int pageSize, @RequestBody TbGoods tbGoods) {
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        tbGoods.setSellerId(sellerId);
        return tbGoodsService.findPage(pageNum, pageSize, tbGoods);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public TbGoods findOne(Long id) {
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        return tbGoodsService.findOne(id, sellerId);
    }

    /**
     * 增加、修改
     *
     * @param tbGoods
     * @return
     */
    @RequestMapping("save")
    public Result save(@RequestBody TbGoods tbGoods) {
        try {
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            tbGoodsService.save(tbGoods, sellerId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "操作失败");
        }
        return new Result(true, "操作成功");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    public Result delete(Long[] ids) {
        try {
            tbGoodsService.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
        return new Result(true, "删除成功");
    }

}
