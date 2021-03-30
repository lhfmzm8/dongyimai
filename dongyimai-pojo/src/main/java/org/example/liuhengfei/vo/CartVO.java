package org.example.liuhengfei.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class CartVO implements Serializable {

    private Long id;

    private Integer num;

    private List<Long> ids;

    private List<SellerInCart> sellers;

    private String username;

    private Map<String, Integer> nums;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<SellerInCart> getSellers() {
        return sellers;
    }

    public void setSellers(List<SellerInCart> sellers) {
        this.sellers = sellers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Integer> getNums() {
        return nums;
    }

    public void setNums(Map<String, Integer> nums) {
        this.nums = nums;
    }

}
