package org.example.liuhengfei.service;

import java.util.Map;

public interface SearchService {

    //分组
    Map<String, Object> grouping(Map searchMap);

    //查询
    Map<String, Object> search(Map searchMap);
}
