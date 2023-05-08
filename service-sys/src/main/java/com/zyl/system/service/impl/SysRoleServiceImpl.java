package com.zyl.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyl.model.system.SysRole;
import com.zyl.model.system.SysUserRole;
import com.zyl.model.vo.AssignRoleVo;
import com.zyl.system.mapper.SysRoleMapper;
import com.zyl.system.mapper.SysUserRoleMapper;
import com.zyl.system.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zyl
 * @date 2023/4/27 15:54
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    private  final SysRoleMapper sysRoleMapper;
    private  final SysUserRoleMapper sysUserRoleMapper;
    @Override
    public Map<String,Object> getRolesByUserId(Long userId) {
        Map<String,Object> map = new HashMap<>();
        //用户角色id
        List<Long> userRoleIds= sysRoleMapper.selectUserRoleIds(userId);
        //所有角色
        List<SysRole> allRoles = sysRoleMapper.selectList(null);

        map.put("userRoleIds" ,userRoleIds);
        map.put("allRoles" ,allRoles);
        return map;
    }

    @Override
    public void doAssign(AssignRoleVo assginRoleVo) {
        //根据用户id删除原来分配的角色
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",assginRoleVo.getUserId());
        sysUserRoleMapper.delete(queryWrapper);
        //获取所有的角色id
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        for (Long roleId : roleIdList) {
            if(roleId != null){
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(assginRoleVo.getUserId());
                sysUserRole.setRoleId(roleId);
                //保存
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }
}
