package com.zyl.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyl.model.system.SysRole;
import com.zyl.model.vo.AssignRoleVo;

import java.util.Map;

/**
 * @author: zyl
 * @date 2023/4/27 15:54
 */

public interface SysRoleService extends IService<SysRole> {
    Map<String,Object> getRolesByUserId(Long userId);

    void doAssign(AssignRoleVo assginRoleVo);
}
