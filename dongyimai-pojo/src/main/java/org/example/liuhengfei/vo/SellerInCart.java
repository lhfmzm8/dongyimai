package org.example.liuhengfei.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
public class SellerInCart implements Serializable {

    private String seller;

    private List<OrderInCart> orders;

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public List<OrderInCart> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderInCart> orders) {
        this.orders = orders;
    }
}
