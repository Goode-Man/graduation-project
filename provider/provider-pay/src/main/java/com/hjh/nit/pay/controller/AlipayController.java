package com.hjh.nit.pay.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.hjh.nit.pay.config.AlipayConfig;
import com.hjh.nit.pay.model.dto.PayDto;
import org.hibernate.annotations.Parameter;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pay")
public class AlipayController {

    private AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,
            AlipayConfig.app_id, AlipayConfig.merchant_private_key,
            AlipayConfig.format, AlipayConfig.charset, AlipayConfig.alipay_public_key,
            AlipayConfig.sign_type); //获得初始化的AlipayClient

    @GetMapping("/paypage")
    public String alipay(PayDto payDto) throws AlipayApiException {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
//        alipayRequest.setReturnUrl("http://localhost:8020/return");必须外网
//        alipayRequest.setNotifyUrl("http://localhost:8020/notify");
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+payDto.getOut_trade_no()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+payDto.getTotal_amount()+"," +
                "    \"subject\":\""+payDto.getSubject()+"\"," +
                "    \"body\":\""+payDto.getBody()+"\"" +
                " }");
        AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest);
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        if(response.isSuccess()){

        } else {

        }
        return result;
    }


}
