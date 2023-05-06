package com.zyl.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyl.model.system.SysMenu;

import java.util.List;

/**
 * @author: zyl
 * @date 2023/4/27 19:34
 */

public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> findMenuList();
}
