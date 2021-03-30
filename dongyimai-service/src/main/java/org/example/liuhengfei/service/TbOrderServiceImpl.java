package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.liuhengfei.mapper.TbAddressMapper;
import org.example.liuhengfei.mapper.TbOrderItemMapper;
import org.example.liuhengfei.mapper.TbOrderMapper;
import org.example.liuhengfei.pojo.TbAddress;
import org.example.liuhengfei.pojo.TbItem;
import org.example.liuhengfei.pojo.TbOrder;
import org.example.liuhengfei.pojo.TbOrderItem;
import org.example.liuhengfei.utils.IdWorker;
import org.example.liuhengfei.vo.OrderInCart;
import org.example.liuhengfei.vo.SellerInCart;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class TbOrderServiceImpl implements TbOrderService {

    @Resource
    private TbAddressMapper tbAddressMapper;

    @Resource
    private TbOrderMapper tbOrderMapper;

    @Resource
    private TbOrderItemMapper tbOrderItemMapper;

    @Resource
    IdWorker idWorker;

    @Resource
    private TbItemService tbItemService;

    @Override
    public List<TbAddress> showAddress(String username) {
        TbAddress tbAddress = new TbAddress();
        tbAddress.setUserId(username);
        return tbAddressMapper.selectByItems(tbAddress);
    }

    @Override
    public void submit(TbOrder template, List<SellerInCart> sellers) throws CloneNotSupportedException {
        for (SellerInCart seller : sellers) {
            TbOrder tbOrder = template.clone();
            long id = idWorker.nextId();
            tbOrder.setOrderId(id);
            tbOrder.setSellerId(seller.getSeller());
            BigDecimal payment = new BigDecimal(0);
            //tbOrderMapper.insertSelective(tbOrder);
            for (OrderInCart orderInCart : seller.getOrders()) {
                payment = payment.add(orderInCart.getTotalFee());
                TbOrderItem tbOrderItem = new TbOrderItem();
                tbOrderItem.setTitle(orderInCart.getTitle());
                tbOrderItem.setPrice(orderInCart.getPrice());
                tbOrderItem.setItemId(orderInCart.getId());
                tbOrderItem.setSellerId(orderInCart.getSeller());
                tbOrderItem.setNum(orderInCart.getNum());
                tbOrderItem.setTotalFee(orderInCart.getTotalFee());
                tbOrderItem.setPicPath(orderInCart.getImage());
                tbOrderItem.setOrderId(id);
                TbItem tbItem = tbItemService.findOne(orderInCart.getId());
                //tbOrderItemMapper.insertSelective(tbOrderItem);
            }
            tbOrder.setPayment(payment);
        }
    }
}
