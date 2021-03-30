package org.example.liuhengfei.listener;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class SMSMessageListener implements MessageListener {

    @Value("${sms.accessKeyId}")
    private String accessKeyId;

    @Value("${sms.accessKeySecret}")
    private String accessKeySecret;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        try {
            request.putQueryParameter("PhoneNumbers", mapMessage.getString("PhoneNumbers"));
            request.putQueryParameter("SignName", mapMessage.getString("SignName"));
            request.putQueryParameter("TemplateCode", mapMessage.getString("TemplateCode"));
            request.putQueryParameter("TemplateParam", mapMessage.getString("TemplateParam"));
            CommonResponse response = client.getCommonResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
