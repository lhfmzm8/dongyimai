package org.example.liuhengfei.service;

import java.util.HashMap;

public interface PayService {

    /**
     * 生成支付宝支付二维码
     *
     * @param outTradeNo 订单号
     * @param totalFee   金额(分)
     * @return
     */
    HashMap<String, String> createNative(String outTradeNo, String totalFee);

}
