package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.liuhengfei.mapper.TbUserMapper;
import org.example.liuhengfei.pojo.TbUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TbUserServiceImpl implements TbUserService {

    @Resource
    private TbUserMapper tbUserMapper;

    /**
     * 增加用户
     *
     * @param user
     */
    @Override
    public void register(TbUser user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        tbUserMapper.insertSelective(user);
    }
}
