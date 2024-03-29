package com.zyl.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyl.common.result.R;
import com.zyl.model.system.SysRole;
import com.zyl.model.vo.AssignRoleVo;
import com.zyl.model.vo.SysRoleQueryVo;
import com.zyl.system.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * author: zyl
 * date 2023/4/27 15:52
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    private final SysRoleService roleService;

    /**
     * 获取全部角色
     *
     * @return 角色列表
     */
    @GetMapping("getAll")
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    public R<List<SysRole>> getAll() {
        List<SysRole> list = roleService.list();
        return R.ok(list);
    }

    /**
     * 根据id获取角色信息（数据回显）
     *
     * @param id id
     * @return role
     */
    @GetMapping("edit/{id}")
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    public R<SysRole> getById(@PathVariable Long id) {
        SysRole sysRole = roleService.getById(id);
        return R.ok(sysRole);
    }

    /**
     * 新增角色
     *
     * @param sysRole 角色信息
     * @return R
     */
    @PostMapping("save")
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    public R save(@RequestBody SysRole sysRole) {
        boolean b = roleService.save(sysRole);
        return b ? R.ok() : R.fail();
    }

    /**
     * 根据id删除角色
     *
     * @param id 角色id
     * @return R
     */
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('bnt.sysRole.remove1')")
    public R remove(@PathVariable Long id) {
        boolean b = roleService.removeById(id);
        return b ? R.ok() : R.fail();
    }

    /**
     * 根据id批量删除角色
     *
     * @param ids id列表
     * @return R
     */
    @DeleteMapping("batchDelete")
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    public R batchRemove(@RequestBody List<Long> ids) {
        boolean b = roleService.removeByIds(ids);
        return b ? R.ok() : R.fail();
    }

    /**
     * 根据id更新角色
     *
     * @param sysRole 角色id
     * @return R
     */
    @PutMapping("update")
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    public R update(@RequestBody SysRole sysRole) {
        sysRole.setUpdateTime(new Date());
        boolean b = roleService.updateById(sysRole);
        return b ? R.ok() : R.fail();
    }

    /**
     * 根据模糊查询分页查询角色信息
     *
     * @param pageNum        页码
     * @param size           页长
     * @param sysRoleQueryVo 条件
     * @return 角色列表
     */
    @PostMapping("page/{pageNum}/{size}")
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    public R<Page<SysRole>> selectPage(@PathVariable Integer pageNum, @PathVariable Integer size, @RequestBody SysRoleQueryVo sysRoleQueryVo) {
        Page<SysRole> page = new Page<>(pageNum, size);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(sysRoleQueryVo.getRoleName()), SysRole::getRoleName, sysRoleQueryVo.getRoleName());
        roleService.page(page, queryWrapper);
        return R.ok(page);
    }

    /**
     * 获取用户角色信息
     * @param userId 用户id
     * @return 角色列表
     */
    @GetMapping("toAssign/{userId}")
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    public R toAssign(@PathVariable Long userId) {
        Map<String,Object> roleMap = roleService.getRolesByUserId(userId);
        return R.ok(roleMap);
    }

    /**
     * 分配权限
     * @param assginRoleVo
     * @return
     */
    @PostMapping("/doAssign")
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    public R doAssign(@RequestBody AssignRoleVo assginRoleVo) {
        roleService.doAssign(assginRoleVo);
        return R.ok();
    }
}
