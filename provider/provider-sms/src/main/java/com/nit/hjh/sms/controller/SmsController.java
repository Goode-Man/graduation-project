package com.nit.hjh.sms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;

import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;


import com.hjh.nit.common.BaseController;
import com.hjh.nit.common.PublicUtil;
import com.hjh.nit.common.RedisUtil;
import com.hjh.nit.common.wrapper.WrapMapper;
import com.hjh.nit.common.wrapper.Wrapper;
import com.nit.hjh.sms.model.dto.SmsSendDetailDTO;
import com.nit.hjh.sms.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/sms")
public class SmsController extends BaseController {



    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${sms.accessId}")
    private String accessKeyId;
    @Value("${sms.accessKey}")
    private String accessSecret;
    private String signName="毕业设计项目";
    @Autowired
    private CodeService codeService;



    @PostMapping("/sendBatchSms")
        public Wrapper<Boolean> sendBatchSms(String phone, String templateCode, String TemplateParam) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        Map<String,String> code=new HashMap<>();
        if(PublicUtil.isNotEmpty(templateCode)){
            code.put("code",TemplateParam);
            stringRedisTemplate.opsForValue().set(phone,TemplateParam,300, TimeUnit.SECONDS);
        }else{
            String codes=codeService.getMsgCode();
            code.put("code",codes);
            stringRedisTemplate.opsForValue().set(phone,codes,300, TimeUnit.SECONDS);

        }

        request.putQueryParameter("TemplateParam", JSON.toJSONString(code));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return WrapMapper.ok();
        } catch (ServerException e) {
            e.printStackTrace();
            return WrapMapper.error();
        } catch (ClientException e) {
            e.printStackTrace();
            return WrapMapper.error();
        }
    }
    @GetMapping("/query")
    public Wrapper<List<SmsSendDetailDTO>> QuerySendDetails(String phone, String sendDate, String pageSize,String currentPage) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("QuerySendDetails");
        request.putQueryParameter("PhoneNumber", phone);
        request.putQueryParameter("SendDate", sendDate);
        request.putQueryParameter("PageSize", pageSize);
        request.putQueryParameter("CurrentPage", currentPage);
        try {
            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
            JSONObject jsonObject=JSONObject.parseObject(response.getData());
            JSONObject jsonObject1= (JSONObject) jsonObject.get("SmsSendDetailDTOs");
            JSONArray jsonArray= (JSONArray) jsonObject1.get("SmsSendDetailDTO");
            List<SmsSendDetailDTO> list=new ArrayList<>();
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject2= (JSONObject) jsonArray.get(i);
                SmsSendDetailDTO smsSendDetailDTO=new SmsSendDetailDTO();
                smsSendDetailDTO.setContent(jsonObject2.get("Content").toString());
                smsSendDetailDTO.setErrCode(jsonObject2.get("ErrCode").toString());
                smsSendDetailDTO.setPhoneNum(jsonObject2.get("PhoneNum").toString());
                smsSendDetailDTO.setReceiveDate(jsonObject2.get("ReceiveDate").toString());
                smsSendDetailDTO.setSendDate(jsonObject2.get("SendDate").toString());
                smsSendDetailDTO.setSendStatus(jsonObject2.get("SendStatus").toString());
                smsSendDetailDTO.setTemplateCode(jsonObject2.get("TemplateCode").toString());
                list.add(smsSendDetailDTO);
            }
            return WrapMapper.ok(list);
        } catch (ServerException e) {
            e.printStackTrace();
            return WrapMapper.error(e.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return WrapMapper.error(e.toString());
        }
    }

//    @PostMapping("/sendsBatchSms")
//    public void sendBatchSms(String phone, String templateCode, String TemplateParam) {
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        //request.setProtocol(ProtocolType.HTTPS);
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//        request.putQueryParameter("PhoneNumberJson", phone.toString());
//        request.putQueryParameter("SignNameJson", signName);
//        request.putQueryParameter("TemplateCode", templateCode);
//        request.putQueryParameter("TemplateParamJson", TemplateParam.toString());
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//    }
    @PostMapping("/sendSms")
    public Wrapper<Boolean> sendSms(String phone, String templateCode) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return WrapMapper.ok();
        } catch (ServerException e) {
            e.printStackTrace();
            return WrapMapper.error(e.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return WrapMapper.error(e.toString());
        }
    }

}
