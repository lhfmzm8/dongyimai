package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import org.example.liuhengfei.pojo.TbItem;
import org.example.liuhengfei.pojo.TbItemCat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private SolrTemplate solrTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    private ArrayList<String> categoryList;

    @Override
    public Map<String, Object> grouping(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        //构造查询条件
        String keywords = (String) searchMap.get("keywords");
        //去除空格
        keywords = keywords.replace(" ", "");
        Criteria criteria = new Criteria("item_keywords").is(keywords);
        //设置分组选项
        SimpleQuery simpleQuery = new SimpleQuery("*:*");
        simpleQuery.addCriteria(criteria);
        GroupOptions groupOptions = new GroupOptions();
        groupOptions.addGroupByField("item_category");
        simpleQuery.setGroupOptions(groupOptions);
        //分组查询
        GroupPage<TbItem> groupPage = solrTemplate.queryForGroupPage(simpleQuery, TbItem.class);
        GroupResult<TbItem> groupResult = groupPage.getGroupResult("item_category");
        Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();
        List<GroupEntry<TbItem>> contents = groupEntries.getContent();
        categoryList = new ArrayList<>();
        if (contents.size() > 0) {
            if (contents.size() > 1) {
                for (GroupEntry<TbItem> groupEntry : contents) {
                    categoryList.add(groupEntry.getGroupValue());
                }
            } else {
                List<TbItemCat> tbItemCats = redisTemplate.boundHashOps("tbItemCats").values();
                TbItemCat tbItemCat = (TbItemCat) redisTemplate.boundHashOps("tbItemCats").get(contents.get(0).getGroupValue());
                Long id = tbItemCat.getId();
                //查询下级分类
                for (TbItemCat itemCat : tbItemCats) {
                    if (itemCat.getParentId() == id) {
                        categoryList.add(itemCat.getName());
                    }
                }
                //如果没有下级分类，就把当前分类放进去
                if (categoryList.size() == 0) {
                    categoryList.add(tbItemCat.getName());
                }
            }
            map.putAll(search(searchMap));
        }
        return map;
    }

    @Override
    public Map<String, Object> search(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        SimpleHighlightQuery simpleHighlightQuery = new SimpleHighlightQuery();
        //设置高亮选项
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        highlightOptions.setSimplePostfix("</em>");
        simpleHighlightQuery.setHighlightOptions(highlightOptions);
        //构造查询条件
        String keywords = (String) searchMap.get("keywords");
        //去除空格
        keywords = keywords.replace(" ", "");
        Criteria criteria = new Criteria("item_keywords").is(keywords);
        simpleHighlightQuery.addCriteria(criteria);
        //过滤分类;
        List<String> categories = JSONArray.parseArray(searchMap.get("categories").toString(), String.class);
        if (categories.size() > 0) {
            Criteria filterCriteria = new Criteria("item_category").is(categories);
            SimpleFilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
            simpleHighlightQuery.addFilterQuery(filterQuery);
        }
        //过滤品牌
        List<String> tbBrands = JSONArray.parseArray(searchMap.get("tbBrands").toString(), String.class);
        if (tbBrands.size() > 0) {
            Criteria filterCriteria = new Criteria("item_brand").is(tbBrands);
            SimpleFilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
            simpleHighlightQuery.addFilterQuery(filterQuery);
        }
        //过滤规格
        Map<String, String> specs = (Map) searchMap.get("specs");
        if (specs.size() > 0) {
            for (String key : specs.keySet()) {
                Criteria filterCriteria = new Criteria("item_spec_" + key).is(specs.get(key));
                SimpleFilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
                simpleHighlightQuery.addFilterQuery(filterQuery);
            }
        }
        //过滤价格
        String price = (String) searchMap.get("price");
        if (!"".equals(price)) {
            String[] prices = price.split("-");
            //过滤头
            if (!"0".equals(prices[0])) {
                Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(prices[0]);
                FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
                simpleHighlightQuery.addFilterQuery(filterQuery);
            }
            //过滤尾
            if (!"*".equals(prices[1])) {
                Criteria filterCriteria = new Criteria("item_price").lessThanEqual(prices[1]);
                FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
                simpleHighlightQuery.addFilterQuery(filterQuery);
            }
        }
        //分页
        Integer pageNum = (Integer) searchMap.get("pageNum");
        Integer pageSize = (Integer) searchMap.get("pageSize");
        simpleHighlightQuery.setOffset((pageNum - 1) * pageSize);
        simpleHighlightQuery.setRows(pageSize);
        //排序
        String sortValue = (String) searchMap.get("sortValue");//ASC | DESC
        String sortField = (String) searchMap.get("sortField");//排序字段
        if ("ASC".equals(sortValue)) {
            Sort sort = new Sort(Sort.Direction.ASC, "item_" + sortField);
            simpleHighlightQuery.addSort(sort);
        } else if ("DESC".equals(sortValue)) {
            Sort sort = new Sort(Sort.Direction.DESC, "item_" + sortField);
            simpleHighlightQuery.addSort(sort);
        }
        //返回高亮
        HighlightPage<TbItem> highlightPage = solrTemplate.queryForHighlightPage(simpleHighlightQuery, TbItem.class);
        if (highlightPage.getHighlighted() != null) {
            for (HighlightEntry<TbItem> highlightEntry : highlightPage.getHighlighted()) {
                if (highlightEntry.getHighlights().size() > 0 && highlightEntry.getHighlights().get(0).getSnipplets().size() > 0) {
                    highlightEntry.getEntity().setTitle(highlightEntry.getHighlights().get(0).getSnipplets().get(0));
                }
            }
            map.put("rows", highlightPage.getContent());
            //返回分页信息
            map.put("totalPages", highlightPage.getTotalPages());//返回总页数
            map.put("total", highlightPage.getTotalElements());//返回总记录数
            ArrayList<String> tbBrandList;
            ArrayList<String> specList;
            TbItemCat tbItemCat;
            if (categories.size() > 0) {
                categoryList.clear();
                tbItemCat = (TbItemCat) redisTemplate.boundHashOps("tbItemCats").get(categories.get(0));
                if (categories.size() > 1) {
                    Long id = tbItemCat.getId();
                    List<TbItemCat> tbItemCats = redisTemplate.boundHashOps("tbItemCats").values();
                    for (TbItemCat itemCat : tbItemCats) {
                        if (itemCat.getParentId() == id) {
                            categoryList.add(itemCat.getName());
                        }
                    }
                } else {
                    categoryList.add(categories.get(0));
                }
            } else {
                tbItemCat = (TbItemCat) redisTemplate.boundHashOps("tbItemCats").get(categoryList.get(0));
            }
            tbBrandList = (ArrayList) redisTemplate.boundHashOps("tbBrands").get(tbItemCat.getTypeId());
            specList = (ArrayList) redisTemplate.boundHashOps("specs").get(tbItemCat.getTypeId());
            map.put("categoryList", categoryList);
            map.put("tbBrandList", tbBrandList);
            map.put("specList", specList);
        }
        return map;
    }
}
