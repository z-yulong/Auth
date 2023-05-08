package com.zyl.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyl.common.result.R;
import com.zyl.model.system.SysUser;
import com.zyl.model.vo.LoginVo;
import com.zyl.model.vo.SysUserQueryVo;

import java.util.List;
import java.util.Map;

/**
 * @author: zyl
 * @date 2023/4/26 18:13
 */

public interface SysUserService extends IService<SysUser> {

    boolean updateStatus(Long userId, Integer status);

    SysUser getUserByUsername(String username);

    R<Map<String,Object>> login(LoginVo loginVo);

    R<Map<String, Object>> logout(String token);

    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo);

    /**
     * 根据用户id获取用户登录信息
     * @return
     */
    Map<String, Object> getUserInfoByUserId(Long userId);

    /**
     * 根据用户id获取用户按钮权限标识符
     */
    List<String> getUserBtnPermsByUserId(Long id);


}
