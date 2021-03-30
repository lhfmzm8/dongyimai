package org.example.liuhengfei.service;

import org.example.liuhengfei.pojo.TbAddress;
import org.example.liuhengfei.pojo.TbOrder;
import org.example.liuhengfei.vo.SellerInCart;

import java.util.List;

public interface TbOrderService {

    List<TbAddress> showAddress(String username);

    void submit(TbOrder template, List<SellerInCart> sellers) throws CloneNotSupportedException;

}
