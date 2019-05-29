package com.nit.hjh.uac.model.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name="user")
public class User implements UserDetails {
    @Id
    private String uid;
    @Length(min=5)
    private String username;
    @Length(min=5,max=16)
    private String password;
    @Email
    private String email;
    private String phone;
    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "rid" ,foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private Role rid;
    private int state;
    private String cratetime;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        Role role=this.getRid();
        auths.add(new SimpleGrantedAuthority(role.getRname()));
        return auths;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    /**
     * 账户是否过期,过期无法验证
     * @return
     */
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
     * @return
     */
    public boolean isAccountNonLocked() {
        if (state==1){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     * @return
     */
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * 是否被禁用,禁用的用户不能身份验证
     * @return
     */
    public boolean isEnabled() {
        return true;
    }
}
