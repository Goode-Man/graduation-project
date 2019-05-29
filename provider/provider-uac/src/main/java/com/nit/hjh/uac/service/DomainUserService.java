package com.nit.hjh.uac.service;

import com.hjh.nit.common.PublicUtil;
import com.nit.hjh.uac.model.entity.User;
import com.nit.hjh.uac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DomainUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails user=userRepository.findByUsername("s");
        if(PublicUtil.isEmpty(user)){
            throw new UsernameNotFoundException("没有找到该用户");
        }else {
            return user;
        }

    }
}
