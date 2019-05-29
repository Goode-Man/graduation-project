package com.nit.hjh.uacs.service;

import com.hjh.nit.common.PublicUtil;
import com.nit.hjh.uacs.model.entity.User;
import com.nit.hjh.uacs.repository.RoleRepository;
import com.nit.hjh.uacs.repository.UserRepository;
import com.nit.hjh.uacs.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.FieldPosition;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class UserService {


    private JwtUtil jwtTokenUtil;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private final static Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);


    private String tokenHead="cs";

    @Autowired
    public UserService(JwtUtil jwtTokenUtil, UserRepository userRepository,RoleRepository roleRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.roleRepository= roleRepository;
    }


    public String login(String username, String password) {
        return "success";
    }

    public String refresh(String oldToken) {
        return "success";
    }

    public String regist(User user){
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
                return "1";
            }else {
                return "0";
            }
        }else{
                return "用户名已存在";
        }
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
