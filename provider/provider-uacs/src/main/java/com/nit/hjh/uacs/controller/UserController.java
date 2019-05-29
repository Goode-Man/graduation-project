package com.nit.hjh.uacs.controller;


import com.hjh.nit.common.BaseController;
import com.hjh.nit.common.PublicUtil;

import com.hjh.nit.common.RedisUtil;
import com.hjh.nit.common.wrapper.WrapMapper;
import com.hjh.nit.common.wrapper.Wrapper;
import com.nit.hjh.uacs.model.dto.JwtAuthenticationResponse;
import com.nit.hjh.uacs.model.entity.User;
import com.nit.hjh.uacs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
public class UserController extends BaseController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    private String tokenHeader="cs";


    private Long expiration=1800L;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public Wrapper<JwtAuthenticationResponse> createAuthenticationTokens(User user) {
        final String token = userService.login(user.getUsername(),user.getPassword());
//        redisUtil.set(token,user,expiration);
        redisTemplate.opsForValue().set(token,user,expiration, TimeUnit.SECONDS);
        return handleResult(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "/auth/check", method = RequestMethod.POST)
    public Wrapper<Boolean> checkAuthenticationTokens(String token) {
        User user=(User)redisTemplate.opsForValue().get(token);
        if(PublicUtil.isEmpty(user)){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"token过期或无效");
        }else {
            return WrapMapper.ok();
        }

    }

    @RequestMapping(value = "/auth/refresh", method = RequestMethod.POST)
    public Object refreshAndGetAuthenticationToken(HttpServletRequest request) throws AuthenticationException {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = userService.refresh(token);
        if(refreshedToken == null) {
            return null;
        } else {
            return new JwtAuthenticationResponse(refreshedToken);
        }
    }

    @PostMapping(value = "user/regist")
    public Wrapper<Boolean> regist(User user){
        String msg=userService.regist(user);
        if(msg.equals("0")||msg.equals("0")){
            return WrapMapper.ok();
        }else {
            return WrapMapper.error(msg);
        }
    }

    @PostMapping(value = "user/check")
    public Wrapper<Boolean> check(String username,String email,String phone){
        return handleResult(userService.check(username,email,phone));
    }

    @PostMapping(value = "user/forget")
    public Wrapper<Boolean> forget(String uid,String password){
        return handleResult(userService.forget(uid,password));
    }
}
