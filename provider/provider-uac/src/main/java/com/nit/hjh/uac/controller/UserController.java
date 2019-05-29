package com.nit.hjh.uac.controller;


import com.hjh.nit.common.BaseController;
import com.hjh.nit.common.PublicUtil;
import com.hjh.nit.common.RedisUtil;
import com.hjh.nit.common.wrapper.WrapMapper;
import com.hjh.nit.common.wrapper.Wrapper;
import com.nit.hjh.uac.model.dto.JwtAuthenticationResponse;
import com.nit.hjh.uac.model.entity.User;
import com.nit.hjh.uac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin
public class UserController extends BaseController {

    private RedisUtil redisUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Wrapper<JwtAuthenticationResponse> createAuthenticationTokens(User user) {
        final String token = userService.login(user.getUsername(),user.getPassword());
        redisUtil.set(token,user,expiration);
        return handleResult(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "/auth/check", method = RequestMethod.POST)
    public Wrapper<Boolean> checkAuthenticationTokens(String token) {
        User user=(User)redisUtil.get(token);
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
    public Wrapper<Boolean> regist(@Valid User user){
        return handleResult(userService.regist(user));
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
