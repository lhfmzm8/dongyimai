package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.pojo.TbContent;
import org.example.liuhengfei.service.TbContentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("TbContentControllerPortal")
public class TbContentControllerPortal {

    @Reference(timeout = 60000)
    private TbContentService tbContentService;

    /**
     * 根据广告分类ID查询广告列表
     *
     * @param categoryId
     * @return
     */
    @RequestMapping("findByCategoryId")
    public List<TbContent> findByCategoryId(Long categoryId) {
        return tbContentService.findByCategoryId(categoryId);
    }
}
