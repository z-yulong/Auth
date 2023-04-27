package com.zyl.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyl.model.system.SysRole;
import com.zyl.system.mapper.SysRoleMapper;
import com.zyl.system.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * @author: zyl
 * @date 2023/4/27 15:54
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
