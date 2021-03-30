package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.example.liuhengfei.dto.PageResult;
import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.pojo.TbGoods;
import org.example.liuhengfei.pojo.TbItem;
import org.example.liuhengfei.service.TbGoodsService;
import org.example.liuhengfei.service.TbItemService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.HashMap;
import java.util.List;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("TbGoodsController")
public class TbGoodsController {

    @Reference(timeout = 60000)
    private TbGoodsService tbGoodsService;

    @Reference(timeout = 60000)
    private TbItemService tbItemService;

    @Resource
    private JmsTemplate jmsTemplate;

    @Resource
    private Destination solrMQQueue;

    @Resource
    private Destination freeMarkerMQQueue;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("findAll")
    public List<TbGoods> findAll() {
        return tbGoodsService.findAll();
    }

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(int pageNum, int pageSize, @RequestBody TbGoods tbGoods) {
        return tbGoodsService.findPage(pageNum, pageSize, tbGoods);
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public TbGoods findOne(Long id) {
        return tbGoodsService.findOne(id);
    }

    /**
     * 批量修改状态
     *
     * @param status
     * @return
     */
    @RequestMapping("updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            tbGoodsService.updateStatus(ids, status);
            List<TbItem> tbItems = tbItemService.find(ids);
            String jsonString = JSON.toJSONString(tbItems);
            //上架或者下架
            jmsTemplate.send(solrMQQueue, session -> session.createTextMessage(jsonString));
            //删除或者创建静态页面
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("goodsIds",ids);
            hashMap.put("status",status);
            jmsTemplate.send(freeMarkerMQQueue, session -> session.createObjectMessage(hashMap));
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
            tbGoodsService.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
        return new Result(true, "删除成功");
    }

}
