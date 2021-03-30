package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.pojo.TbTypeTemplate;
import org.example.liuhengfei.service.TbTypeTemplateService;
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
@RequestMapping("TbTypeTemplateController")
public class TbTypeTemplateController {

    @Reference(timeout = 60000)
    private TbTypeTemplateService tbTypeTemplateService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("findAll")
    public List<TbTypeTemplate> findAll() {
        return tbTypeTemplateService.findAll();
    }

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(int pageNum, int pageSize, @RequestBody TbTypeTemplate tbTypeTemplate) {
        return tbTypeTemplateService.findPage(pageNum, pageSize, tbTypeTemplate);
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public TbTypeTemplate findOne(Long id) {
        return tbTypeTemplateService.findOne(id);
    }

    /**
     * 获取实体
     *
     * @return
     */
    @RequestMapping("findTbTypeTemplateOptions")
    public List<Map> findTbTypeTemplateOptions() {
        return tbTypeTemplateService.selectOptionList();
    }

    /**
     * 增加、修改
     *
     * @param tbTypeTemplate
     * @return
     */
    @RequestMapping("save")
    public Result add(@RequestBody TbTypeTemplate tbTypeTemplate) {
        try {
            tbTypeTemplateService.save(tbTypeTemplate);
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
            tbTypeTemplateService.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
        return new Result(true, "删除成功");
    }

}
