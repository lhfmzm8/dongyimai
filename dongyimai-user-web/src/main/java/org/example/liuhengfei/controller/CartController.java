package org.example.liuhengfei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.pojo.TbItem;
import org.example.liuhengfei.service.TbItemService;
import org.example.liuhengfei.utils.CookieUtil;
import org.example.liuhengfei.vo.CartVO;
import org.example.liuhengfei.vo.OrderInCart;
import org.example.liuhengfei.vo.SellerInCart;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("CartController")
public class CartController {

    public static final String ANONYMOUS_USER = "anonymousUser";

    @Reference(timeout = 60000)
    private TbItemService tbItemService;

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private HttpServletResponse httpServletResponse;

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("add")
    public Result add(@RequestBody CartVO cartVO) {
        try {
            Integer num = cartVO.getNum();
            OrderInCart order = new OrderInCart();
            order.setId(cartVO.getId());
            updateOrder(order);
            if (order.getResult().isSuccess()) {
                String username = cartVO.getUsername();
                if (username == null) {
                    String cartString = CookieUtil.getCookieValue(httpServletRequest, "cart", true);
                    CartVO cart = JSON.parseObject(cartString, CartVO.class);
                    CookieUtil.setCookie(httpServletRequest, httpServletResponse, "cart",
                            JSON.toJSONString(createCart(cart, order, num)), 3600 * 24, true);
                } else {
                    CartVO cart = (CartVO) redisTemplate.boundHashOps("carts").get(username);
                    redisTemplate.boundHashOps("carts").put(username, createCart(cart, order, num));
                }
            } else {
                return order.getResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
        return new Result(true, "添加成功");
    }

    @RequestMapping("delete")
    public Result delete(@RequestBody CartVO cart) {
        List<Long> ids = cart.getIds();
        List<SellerInCart> sellers = cart.getSellers();
        String username = cart.getUsername();
        try {
            for (Long id : ids) {
                for (SellerInCart seller : sellers) {
                    List<OrderInCart> orders = seller.getOrders();
                    orders.removeIf(order -> order.getId().equals(id));
                }
            }
            sellers.removeIf(seller -> seller.getOrders().size() == 0);
            String cartString = JSON.toJSONString(sellers);
            if (username == null) {
                CookieUtil.setCookie(httpServletRequest, httpServletResponse, "cart", cartString,
                        3600 * 24, true);
            } else {
                redisTemplate.boundHashOps("carts").put(username, cartString);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
        return new Result(true, "删除成功");
    }

    @RequestMapping("show")
    public CartVO show() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String cartInCookieString = CookieUtil.getCookieValue(httpServletRequest, "cart", true);
        CartVO cartInCookie = JSON.parseObject(cartInCookieString, CartVO.class);
        if (!ANONYMOUS_USER.equals(username)) {
            CartVO cartInRedis = (CartVO) redisTemplate.boundHashOps("carts").get(username);
            if (cartInCookie != null) {
                List<SellerInCart> sellersInCookie = cartInCookie.getSellers();
                Map<String, Integer> numsInCookie = cartInCookie.getNums();
                for (SellerInCart seller : sellersInCookie) {
                    List<OrderInCart> orders = seller.getOrders();
                    for (OrderInCart order : orders) {
                        updateOrder(order);
                        Integer num = numsInCookie.get(order.getId() + "");
                        cartInRedis = createCart(cartInRedis, order, num);
                    }
                }
                redisTemplate.boundHashOps("carts").put(username, cartInRedis);
            } else if (cartInRedis == null) {
                cartInRedis = new CartVO();
            }
            cartInRedis.setUsername(username);
            CookieUtil.deleteCookie(httpServletRequest, httpServletResponse, "cart");
            return cartInRedis;
        }
        if (cartInCookie != null) {
            List<SellerInCart> sellersInCookie = cartInCookie.getSellers();
            for (SellerInCart seller : sellersInCookie) {
                List<OrderInCart> orders = seller.getOrders();
                for (OrderInCart order : orders) {
                    updateOrder(order);
                }
            }
        }
        return cartInCookie;
    }

    @RequestMapping("toOrder")
    public Result toOrder(@RequestBody CartVO cart) {
        try {
            List<SellerInCart> sellers = cart.getSellers();
            Map<String, Integer> nums = cart.getNums();
            for (SellerInCart seller : sellers) {
                List<OrderInCart> orders = seller.getOrders();
                for (OrderInCart order : orders) {
                    updateOrder(order);
                    order.setTotalFee(order.getPrice().multiply(new BigDecimal(nums.get(order.getId() + ""))));
                }
            }
            redisTemplate.boundHashOps("carts").put(cart.getUsername(), cart);
            return new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "操作失败");
        }
    }

    @RequestMapping("test")
    public void test() {
    }

    private void updateOrder(OrderInCart order) {
        TbItem tbItem = tbItemService.findOne(order.getId());
        Result result;
        if (tbItem == null) {
            result = new Result(false, "商品不存在");
        } else if (!"1".equals(tbItem.getStatus())) {
            result = new Result(false, "商品已下架");
        } else if (tbItem.getNum() == 0) {
            result = new Result(false, "商品已售空");
        } else {
            BeanUtils.copyProperties(tbItem, order);
            result = new Result(true, "更新成功");
        }
        order.setResult(result);
    }

    private CartVO createCart(CartVO cart, OrderInCart orderInCart, Integer num) {
        Map<String, Integer> nums;
        List<SellerInCart> sellers;
        if (cart == null) {
            cart = new CartVO();
            nums = new HashMap<>();
            nums.put(orderInCart.getId() + "", num);
            cart.setNums(nums);
            sellers = new ArrayList<>();
            sellers.add(createSeller(orderInCart));
            cart.setSellers(sellers);
        } else {
            nums = cart.getNums();
            sellers = cart.getSellers();
            Long id = orderInCart.getId();
            if (nums.get(id + "") != null) {
                num += nums.get(id + "");
                if (num > orderInCart.getNum()) {
                    num = orderInCart.getNum();
                }
            } else {
                boolean temp = true;
                for (SellerInCart seller : sellers) {
                    if (seller.getSeller().equals(orderInCart.getSeller())) {
                        seller.getOrders().add(orderInCart);
                        temp = false;
                        break;
                    }
                }
                if (temp) {
                    sellers.add(createSeller(orderInCart));
                }
            }
            nums.put(id + "", num);
        }
        return cart;
    }

    private SellerInCart createSeller(OrderInCart orderInCart) {
        SellerInCart seller = new SellerInCart();
        seller.setSeller(orderInCart.getSeller());
        List<OrderInCart> orders = new ArrayList<>();
        orders.add(orderInCart);
        seller.setOrders(orders);
        return seller;
    }

}
