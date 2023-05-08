package com.zyl.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyl.common.result.ResultCode;
import com.zyl.model.system.SysUser;
import com.zyl.system.custom.CustomUser;
import com.zyl.system.exception.MyException;
import com.zyl.system.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author: zyl
 * @date 2023/5/8 18:12
 */
@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("username", username);
        SysUser sysUser = sysUserService.getOne(qw);
        if (null == sysUser) {
            throw new MyException(ResultCode.ACCOUNT_ERROR);
        }
        if (sysUser.getStatus().intValue() == 0) {
            throw new MyException(ResultCode.ACCOUNT_STOP);
        }
        return new CustomUser(sysUser, Collections.emptyList());
    }

}
