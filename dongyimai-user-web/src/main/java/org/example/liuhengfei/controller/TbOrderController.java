package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.pojo.TbAddress;
import org.example.liuhengfei.pojo.TbOrder;
import org.example.liuhengfei.service.TbOrderService;
import org.example.liuhengfei.vo.CartVO;
import org.example.liuhengfei.vo.OrderInCart;
import org.example.liuhengfei.vo.OrderVO;
import org.example.liuhengfei.vo.SellerInCart;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("TbOrderController")
public class TbOrderController {

    @Reference
    private TbOrderService tbOrderService;

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("show")
    public OrderVO show() {
        OrderVO order = new OrderVO();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TbAddress> tbAddresses = tbOrderService.showAddress(username);
        CartVO cart = (CartVO) redisTemplate.boundHashOps("carts").get(username);
        List<SellerInCart> sellers = cart.getSellers();
        Map<String, Integer> nums = cart.getNums();
        for (SellerInCart seller : sellers) {
            List<OrderInCart> ordersInCart = seller.getOrders();
            for (OrderInCart orderInCart : ordersInCart) {
                if (orderInCart.getChecked())
                    orderInCart.setNum(nums.get(orderInCart.getId() + ""));
            }
            ordersInCart.removeIf(orderInCart -> !orderInCart.getChecked());
        }
        sellers.removeIf(seller -> seller.getOrders().size() == 0);
        order.setSellers(sellers);
        order.setUsername(username);
        order.setAddresses(tbAddresses);
        return order;
    }

    @RequestMapping("submit")
    public Result submit(@RequestBody OrderVO order) {
        try {
            TbOrder template = new TbOrder();
            template.setUserId(order.getUsername());
            template.setCreateTime(new Date());
            template.setUpdateTime(new Date());
            template.setSourceType("2");//来源类型 PC
            template.setStatus("1");//支付状态，未支付
            template.setReceiverAreaName(order.getAddress().getAddress());
            template.setReceiverMobile(order.getAddress().getMobile());
            template.setReceiver(order.getAddress().getContact());
            tbOrderService.submit(template, order.getSellers());
            //redisTemplate.boundHashOps("carts").delete(order.getUsername());
            return new Result(true, "提交成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "提交失败");
        }
    }

}
