package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.pojo.TbSpecification;
import org.example.liuhengfei.service.TbSpecificationService;
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
@RequestMapping("TbSpecificationController")
public class TbSpecificationController {

    @Reference(timeout = 60000)
    private TbSpecificationService tbSpecificationService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("findAll")
    public List<TbSpecification> findAll() {
        return tbSpecificationService.findAll();
    }

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(int pageNum, int pageSize, @RequestBody TbSpecification tbSpecification) {
        return tbSpecificationService.findPage(pageNum, pageSize, tbSpecification);
    }

    /**
     * 返回列表选项
     */
    @RequestMapping("selectOptions")
    public List<Map> selectOptions() {
        return tbSpecificationService.selectOptions();
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public TbSpecification findOne(Long id) {
        return tbSpecificationService.findOne(id);
    }

    /**
     * 增加、修改
     *
     * @param tbSpecification
     * @return
     */
    @RequestMapping("save")
    public Result save(@RequestBody TbSpecification tbSpecification) {
        try {
            tbSpecificationService.save(tbSpecification);
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
            tbSpecificationService.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
        return new Result(true, "删除成功");
    }

}
