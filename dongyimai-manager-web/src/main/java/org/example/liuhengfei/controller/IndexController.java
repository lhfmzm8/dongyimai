package org.example.liuhengfei.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("IndexController")
public class IndexController {

    @RequestMapping("showName")
    public Map showName() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("loginName", name);
        return hashMap;
    }
}
