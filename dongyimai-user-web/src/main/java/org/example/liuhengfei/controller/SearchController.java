package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.service.SearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("SearchController")
public class SearchController {

    @Reference(timeout = 60000)
    private SearchService searchService;

    @RequestMapping("grouping")
    public Map<String, Object> grouping(@RequestBody Map searchMap) {
        return searchService.grouping(searchMap);
    }

    @RequestMapping("search")
    public Map<String, Object> search(@RequestBody Map searchMap) {
        return searchService.search(searchMap);
    }
}
