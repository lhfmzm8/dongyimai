package org.example.liuhengfei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author Administrator
 */
@Service
public class PayServiceImpl implements PayService {

    @Resource
    private AlipayClient alipayClient;

    @Override
    public HashMap<String, String> createNative(String outTradeNo, String totalFee) {
        //创建API对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        HashMap<String, String> trade = new HashMap<>(16);
        trade.put("out_trade_no", outTradeNo);
        trade.put("total_amount", totalFee);
        trade.put("subject", "东易买开心购物");
        trade.put("store_id", "xa_001");
        trade.put("timeout_express", "90m");
        request.setBizContent(JSON.toJSONString(trade));
        try {
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            String code = response.getCode();
            if ("10000".equals(code)) {
                trade.put("qrcode", response.getQrCode());
            } else {
                throw new RuntimeException("预下单请求失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return trade;
    }
}
