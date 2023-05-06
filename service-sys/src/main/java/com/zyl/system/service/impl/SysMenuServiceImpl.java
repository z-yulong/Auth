package com.zyl.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyl.common.helper.MenuHelper;
import com.zyl.model.system.SysMenu;
import com.zyl.system.mapper.SysMenuMapper;
import com.zyl.system.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zyl
 * @date 2023/4/27 19:34
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Override
    public List<SysMenu> findMenuList() {
        List<SysMenu> sysMenus = baseMapper.selectList(null);
        List<SysMenu> r = MenuHelper.buildTree(sysMenus);
        return r;
    }
}
