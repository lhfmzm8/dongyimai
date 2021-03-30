package org.example.liuhengfei.vo;

import org.example.liuhengfei.pojo.TbAddress;

import java.io.Serializable;
import java.util.List;

public class OrderVO implements Serializable {

    private String username;

    private List<TbAddress> addresses;

    private TbAddress address;

    private List<SellerInCart> sellers;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<TbAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<TbAddress> addresses) {
        this.addresses = addresses;
    }

    public TbAddress getAddress() {
        return address;
    }

    public void setAddress(TbAddress address) {
        this.address = address;
    }

    public List<SellerInCart> getSellers() {
        return sellers;
    }

    public void setSellers(List<SellerInCart> sellers) {
        this.sellers = sellers;
    }
}
