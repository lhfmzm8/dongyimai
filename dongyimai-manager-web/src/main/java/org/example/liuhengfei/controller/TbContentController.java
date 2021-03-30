package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.pojo.TbContent;
import org.example.liuhengfei.service.TbContentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("TbContentController")
public class TbContentController {

    @Reference
    private TbContentService tbContentService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("findAll")
    public List<TbContent> findAll() {
        return tbContentService.findAll();
    }

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(int pageNum, int pageSize, @RequestBody TbContent tbContent) {
        return tbContentService.findPage(pageNum, pageSize, tbContent);
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public TbContent findOne(Long id) {
        return tbContentService.findOne(id);
    }

    /**
     * 增加
     *
     * @param tbContent
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestBody TbContent tbContent) {
        try {
            tbContentService.add(tbContent);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
        return new Result(true, "添加成功");
    }

    /**
     * 修改
     *
     * @param tbContent
     * @return
     */
    @RequestMapping("update")
    public Result update(@RequestBody TbContent tbContent) {
        try {
            tbContentService.update(tbContent);
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
            tbContentService.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
        return new Result(true, "删除成功");
    }

}
