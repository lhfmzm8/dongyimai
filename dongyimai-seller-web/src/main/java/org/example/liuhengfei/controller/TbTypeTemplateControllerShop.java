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
@RequestMapping("TbTypeTemplateControllerShop")
public class TbTypeTemplateControllerShop {

    @Reference(timeout = 60000)
    private TbTypeTemplateService tbTypeTemplateService;

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
     * 获取规格及规格选项
     *
     * @param id
     * @return
     */
    @RequestMapping("findTbSpecifications")
    public List<Map> findTbSpecifications(Long id) {
        return tbTypeTemplateService.selectOptionList(id);
    }

}
