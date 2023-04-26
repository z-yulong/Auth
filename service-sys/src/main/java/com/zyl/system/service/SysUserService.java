package com.zyl.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyl.common.result.R;
import com.zyl.model.system.SysUser;

/**
 * @author: zyl
 * @date 2023/4/26 18:13
 */

public interface SysUserService extends IService<SysUser> {

    boolean updateStatus(Long userId, Integer status);
}