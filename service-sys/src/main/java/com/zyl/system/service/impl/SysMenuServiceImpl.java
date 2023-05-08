package com.zyl.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyl.common.helper.MenuHelper;
import com.zyl.model.system.SysMenu;
import com.zyl.model.system.SysRoleMenu;
import com.zyl.model.vo.AssignMenuVo;
import com.zyl.system.mapper.SysMenuMapper;
import com.zyl.system.mapper.SysRoleMenuMapper;
import com.zyl.system.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author: zyl <br>
 * date 2023/4/27 19:34
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
   private final SysRoleMenuMapper sysRoleMenuMapper;

    public SysMenuServiceImpl(SysRoleMenuMapper sysRoleMenuMapper) {
        this.sysRoleMenuMapper = sysRoleMenuMapper;
    }

    @Override
    public List<SysMenu> findMenuList() {
        List<SysMenu> sysMenus = baseMapper.selectList(null);
        return MenuHelper.buildTree(sysMenus);
    }

    @Override
    public List<SysMenu> getRoleMenuList(Long roleId) {
        //1.获取所有的权限列表
        List<SysMenu> sysMenus = baseMapper.selectList(null);

        List<SysMenu> tree = MenuHelper.buildTree(sysMenus);

        //根据角色id获取角色权限
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId));
        List<Long> roleMenuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());

        for (SysMenu sysMenu : sysMenus) {
            Long id = sysMenu.getId();
            if(roleMenuIds.contains(id)){
                sysMenu.setSelect(true);
            }
        }
        return tree;
    }

    @Override
    public void doAssign(AssignMenuVo assignMenuVo) {
        //删除已分配的权限
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",assignMenuVo.getRoleId()));
        //遍历所有已选择的权限id
        for (Long menuId : assignMenuVo.getMenuIdList()) {
            if(menuId != null){
                //创建SysRoleMenu对象
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
                //添加新权限
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }
}
