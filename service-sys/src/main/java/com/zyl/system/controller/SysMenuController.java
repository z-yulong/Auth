package com.zyl.system.controller;

import com.zyl.common.result.R;
import com.zyl.model.system.SysMenu;
import com.zyl.system.service.SysMenuService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author: zyl
 * @date 2023/4/27 19:36
 */
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    private final SysMenuService menuService;

    public SysMenuController(SysMenuService menuService) {
        this.menuService = menuService;
    }
    /**
     * 添加菜单
     * @param sysMenu 菜单
     */
    @PostMapping("save")
    public R<SysMenu> save(@RequestBody SysMenu sysMenu) {
        boolean b = menuService.save(sysMenu);
        return b ? R.ok() : R.fail();
    }

    /**
     * 根据id查询菜单
     *
     * @param id 菜单id
     * @return R&lt;SysMenu&gt;
     */
    @GetMapping("getById/{id}")
    public R<SysMenu> getById(@PathVariable Long id) {
        SysMenu menu = menuService.getById(id);
        return R.ok(menu);
    }

    /**
     * 根据id删除菜单
     * @param id 菜单id
     */
    @DeleteMapping("delete/{id}")
    public R<SysMenu> remove(@PathVariable Long id) {
        boolean b = menuService.removeById(id);
        return b ? R.ok() : R.fail();
    }

    /**
     * 更新菜单
     * @param sysMenu 菜单
     */
    @PutMapping("update")
    public R<SysMenu> update(@RequestBody SysMenu sysMenu) {
        //设置更新时间
        sysMenu.setUpdateTime(null);
        boolean b = menuService.updateById(sysMenu);
        return b ? R.ok() : R.fail();
    }

}