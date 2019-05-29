package com.nit.hjh.uac.service;

import com.hjh.nit.common.PublicUtil;
import com.hjh.nit.common.RedisUtil;
import com.nit.hjh.uac.model.entity.User;
import com.nit.hjh.uac.repository.RoleRepository;
import com.nit.hjh.uac.repository.UserRepository;
import com.nit.hjh.uac.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.FieldPosition;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class UserService {
    private RedisUtil redisUtil;

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtUtil jwtTokenUtil;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private final static Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtTokenUtil, UserRepository userRepository,RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.roleRepository= roleRepository;
    }


    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken=new UsernamePasswordAuthenticationToken(username,password);
        final Authentication authentication =authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        if(userDetails!=null){
            final String token = jwtTokenUtil.generateToken(userDetails);
            return token;
        }else {
            return null;
        }
    }

    public String refresh(String oldToken) {
        final String token=oldToken.substring(tokenHead.length());
        String username=jwtTokenUtil.getUsernameFromToken(token);
        User user= (User) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    public boolean regist(User user){
        User mUser=userRepository.findByUsername(user.getUsername());
        if(mUser==null){
            Calendar rightNow = Calendar.getInstance();
            StringBuffer sb = new StringBuffer();
            dateFormat.format(rightNow.getTime(), sb,HELPER_POSITION);
            user.setUid(sb+"01");
            user.setRid(roleRepository.findByRid(1));
            user.setState(1);
            user.setCratetime(dateFormat.format(rightNow.getTime()));
            User mmUser=userRepository.save(user);
            if(mmUser !=null){
                return true;
            }else {
                return false;
            }
        }else{
                return false;
        }
    }

    public boolean checkRedis(String token,String username){
        if(redisUtil.hasKey(token)){
            User user= (User) redisUtil.get(token);
            if(PublicUtil.isNotEmpty(user)){
                if(user.getUsername().equals(username)){
                    return true;
                }
            }
        }
        return false;

    }

    public boolean check(String username,String email,String phone){
        User user=userRepository.findByUsername(username);
        if(PublicUtil.isNotEmpty(email)){
            if(user.getEmail().equals(email)==true){
                return true;
            }else {
                return false;
            }
        }else {
            if(user.getPhone().equals(phone)==true){
                return true;
            }else {
                return false;
            }
        }

    }

    @Transactional
    public boolean forget(String uid, String password) {
        return userRepository.updatePasswordByUid(uid,password);
    }
}
