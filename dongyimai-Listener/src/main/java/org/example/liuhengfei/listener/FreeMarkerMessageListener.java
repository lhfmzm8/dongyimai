package org.example.liuhengfei.listener;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.example.liuhengfei.mapper.TbGoodsDescMapper;
import org.example.liuhengfei.mapper.TbGoodsMapper;
import org.example.liuhengfei.mapper.TbItemCatMapper;
import org.example.liuhengfei.mapper.TbItemMapper;
import org.example.liuhengfei.pojo.TbGoods;
import org.example.liuhengfei.pojo.TbGoodsDesc;
import org.example.liuhengfei.pojo.TbItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeMarkerMessageListener implements MessageListener {

    @Value("${freemarker.htmlPath}")
    private String htmlPath;

    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Resource
    private TbGoodsMapper tbGoodsMapper;

    @Resource
    private TbGoodsDescMapper tbGoodsDescMapper;

    @Resource
    private TbItemCatMapper tbItemCatMapper;

    @Resource
    private TbItemMapper tbItemMapper;

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            Map<String, Object> map = (Map<String, Object>) objectMessage.getObject();
            Long[] goodsIds = (Long[]) map.get("goodsIds");
            for (Long goodsId : goodsIds) {
                if ("1".equals(map.get("status"))) {
                    productHtml(goodsId);
                } else {
                    deleteHtml(goodsId);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void productHtml(Long goodsId) {

        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        HashMap<String, Object> hashMap = new HashMap<>();
        //获取商品信息
        TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(goodsId);
        TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsId);
        tbGoods.setTbGoodsDesc(tbGoodsDesc);
        //获取商品分类
        String tbItemCatName1 = tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id()).getName();
        String tbItemCatName2 = tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id()).getName();
        String tbItemCatName3 = tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName();
        //获取SKU
        TbItem tbItem = new TbItem();
        tbItem.setGoodsId(goodsId);
        List<TbItem> tbItems = tbItemMapper.selectByItems(tbItem);
        hashMap.put("tbGoods", tbGoods);
        hashMap.put("tbItemCatName1", tbItemCatName1);
        hashMap.put("tbItemCatName2", tbItemCatName2);
        hashMap.put("tbItemCatName3", tbItemCatName3);
        hashMap.put("tbItems", tbItems);
        try {
            //读取模板
            Template template = configuration.getTemplate("item.ftl");
            FileOutputStream fileOutputStream = new FileOutputStream(htmlPath + goodsId + ".html");
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
                try {
                    template.process(hashMap, outputStreamWriter);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
                outputStreamWriter.flush();
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteHtml(Long goodsId) {
        File file = new File(htmlPath + goodsId + ".html");
        file.delete();
    }

}
