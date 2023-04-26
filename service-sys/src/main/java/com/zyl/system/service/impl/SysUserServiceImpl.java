package com.zyl.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyl.common.result.R;
import com.zyl.model.system.SysUser;
import com.zyl.system.mapper.SysUserMapper;
import com.zyl.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zyl
 * @date 2023/4/26 18:12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    /**
     * 更新用户状态
     * @param id userId
     * @param status 状态
     * @return R
     */
    public boolean updateStatus(Long id, Integer status) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setStatus(status);
        int i = baseMapper.updateById(sysUser);
        return i != 0;
    }
}
