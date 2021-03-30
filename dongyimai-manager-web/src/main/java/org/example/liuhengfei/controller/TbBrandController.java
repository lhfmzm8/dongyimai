package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.pojo.TbBrand;
import org.example.liuhengfei.service.TbBrandService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("TbBrandController")
public class TbBrandController {

    @Reference(timeout = 60000)
    private TbBrandService tbBrandService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("findAll")
    public List<TbBrand> findAll() {
        return tbBrandService.findAll();
    }

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(int pageNum, int pageSize, @RequestBody TbBrand tbBrand) {
        return tbBrandService.findPage(pageNum, pageSize, tbBrand);
    }

    /**
     * 返回列表选项
     */
    @RequestMapping("selectOptions")
    public List<Map> selectOptions() {
        return tbBrandService.selectOptions();
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public TbBrand findOne(@RequestBody Long id) {
        return tbBrandService.findOne(id);
    }

    /**
     * 增加
     *
     * @param tbBrand
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestBody TbBrand tbBrand) {
        try {
            tbBrandService.add(tbBrand);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
        return new Result(true, "添加成功");
    }

    /**
     * 修改
     *
     * @param tbBrand
     * @return
     */
    @RequestMapping("update")
    public Result update(@RequestBody TbBrand tbBrand) {
        try {
            tbBrandService.update(tbBrand);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
        return new Result(true, "修改成功");
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
            tbBrandService.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
        return new Result(true, "删除成功");
    }

}
