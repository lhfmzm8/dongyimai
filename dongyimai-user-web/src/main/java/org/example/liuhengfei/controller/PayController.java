package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.service.PayService;
import org.example.liuhengfei.utils.IdWorker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("PayController")
public class PayController {

    @Reference(timeout = 60000)
    private PayService payService;

    @Resource
    private IdWorker idWorker;

    @RequestMapping("pay")
    public HashMap<String, String> pay() {
        long id = idWorker.nextId();
        return payService.createNative(id + "", "1");
    }

}
