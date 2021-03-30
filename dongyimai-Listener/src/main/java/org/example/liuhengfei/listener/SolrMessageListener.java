package org.example.liuhengfei.listener;

import com.alibaba.fastjson.JSON;
import org.example.liuhengfei.pojo.TbItem;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;
import java.util.Map;

public class SolrMessageListener implements MessageListener {

    @Resource
    private SolrTemplate solrTemplate;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            List<TbItem> tbItems = JSON.parseArray(textMessage.getText(), TbItem.class);
            for (TbItem tbItem : tbItems) {
                if ("1".equals(tbItem.getStatus())) {
                    Map map = JSON.parseObject(tbItem.getSpec());
                    tbItem.setSpecMap(map);
                    solrTemplate.saveBean(tbItem);
                } else {
                    Criteria criteria = new Criteria("id").is(tbItem.getId());
                    SimpleQuery simpleQuery = new SimpleQuery("*:*");
                    simpleQuery.addCriteria(criteria);
                    solrTemplate.delete(simpleQuery);
                }
                solrTemplate.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
