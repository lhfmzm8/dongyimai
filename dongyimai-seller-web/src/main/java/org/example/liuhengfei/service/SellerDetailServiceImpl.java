package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.pojo.TbSeller;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class SellerDetailServiceImpl implements UserDetailsService {

    @Reference
    private TbSellerService tbSellerService;

    @Override
    public UserDetails loadUserByUsername(String sellerId) throws UsernameNotFoundException {
        ArrayList<SimpleGrantedAuthority> al = new ArrayList<>();
        al.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        TbSeller tbSeller = tbSellerService.findOne(sellerId);
        if ("1".equals(tbSeller.getStatus())) {
            return new User(sellerId, tbSeller.getPassword(), al);
        }
        return null;
    }


}