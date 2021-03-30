package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.remoting.exchange.Response;
import com.alibaba.fastjson.JSON;
import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.pojo.TbUser;
import org.example.liuhengfei.service.TbUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.MapMessage;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("TbUserController")
public class TbUserController {

    @Reference(timeout = 60000)
    private TbUserService tbUserService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private JmsTemplate jmsTemplate;

    @Resource
    private Destination smsMQQueue;

    @Value("${sms.templateCode}")
    private String templateCode;

    @Value("${sms.signName}")
    private String signName;

    /**
     * 发送验证码
     *
     * @param phoneNumbers
     */
    @RequestMapping("sendCode")
    public void sendCode(final String phoneNumbers) {
        final String code = (int) (Math.random() * 100000) + "";
        redisTemplate.boundHashOps("codes").put(phoneNumbers, code);
        jmsTemplate.send(smsMQQueue, session -> {
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("PhoneNumbers", phoneNumbers);
            mapMessage.setString("TemplateCode", templateCode);
            mapMessage.setString("SignName", signName);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("code", code);
            mapMessage.setString("TemplateParam", JSON.toJSONString(hashMap));
            return mapMessage;
        });
    }

    /**
     * 注册
     *
     * @param tbUser
     * @return
     */
    @RequestMapping("register")
    public Result register(@RequestBody TbUser tbUser, String code1) {
        String code2 = (String) redisTemplate.boundHashOps("codes").get(tbUser.getPhone());
        if (code2 != null && code2.equals(code1)) {
            try {
                tbUserService.register(tbUser);
                return new Result(true, "注册成功");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, "注册失败");
            }
        }
        return new Result(false, "验证码错误");
    }

    /**
     * 获取用户名
     *
     * @return
     */
    @RequestMapping("showName")
    public Map<String, String> showName() {
        HashMap<String, String> hashMap = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication == null ? null : authentication.getName();
        hashMap.put("username", username);
        return hashMap;
    }

}
