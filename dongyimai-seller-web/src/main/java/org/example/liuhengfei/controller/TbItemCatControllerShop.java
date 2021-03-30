package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.pojo.TbItemCat;
import org.example.liuhengfei.service.TbItemCatService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品类目controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("TbItemCatControllerShop")
public class TbItemCatControllerShop {

    @Reference(timeout = 60000)
    private TbItemCatService tbItemCatService;

    /**
     * 获取所有
     *
     * @return
     */
    @RequestMapping("findAll")
    public List<TbItemCat> findAll() {
        return tbItemCatService.findAll();
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public TbItemCat findOne(Long id) {
        return tbItemCatService.findOne(id);
    }

    /**
     * 获取列表
     *
     * @param parentId
     * @return
     */
    @RequestMapping("findByParentId")
    public List<TbItemCat> findByParentId(Long parentId) {
        return tbItemCatService.findByParentId(parentId);
    }

    /**
     * 增加、修改
     *
     * @param tbItemCat
     * @return
     */
    @RequestMapping("save")
    public Result save(@RequestBody TbItemCat tbItemCat) {
        try {
            tbItemCatService.save(tbItemCat);
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
            tbItemCatService.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
        return new Result(true, "删除成功");
    }

}
