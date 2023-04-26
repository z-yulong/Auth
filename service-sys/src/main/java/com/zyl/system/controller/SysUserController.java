package com.zyl.system.controller;

import com.zyl.common.result.R;
import com.zyl.common.util.MD5;
import com.zyl.model.system.SysUser;
import com.zyl.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zyl
 * @date 2023/4/26 18:17
 */
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    private final SysUserService sysUserService;
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 更新用户状态
     * @param userId userId
     * @param status 状态
     * @return R
     */
    @PutMapping("/updateStatus/{userId}/{status}")
    public R updateStatus(@PathVariable Long userId, @PathVariable Integer status) {
        boolean b = sysUserService.updateStatus(userId, status);
        return b ? R.ok() : R.fail();
    }

    /**
     * 根据id删除用户（逻辑删除）
     *
     * @param userId userId
     * @return R
     */
    @DeleteMapping("/deleteUser/{userId}")
    public R deleteUser(@PathVariable Long userId) {
        boolean b = sysUserService.removeById(userId);
        return b ? R.ok() : R.fail();
    }

    /**
     * 根据id查询用户
     * @param userId userId
     * @return R
     */
    @GetMapping("/getUser/{userId}")
    public R getUser(@PathVariable Long userId) {
        SysUser user = sysUserService.getById(userId);
        return R.ok(user);
    }

    /**
     * 根据id更新用户
     *
     * @param user user
     * @return R
     */
    @PutMapping("/getUser/{userId}")
    public R updateUser(@RequestBody SysUser user) {
        user.setUpdateTime(null);
        boolean b = sysUserService.updateById(user);
        return b ? R.ok() : R.fail();
    }

    /**
     * 添加用户
     * @param user user
     * @return R
     */
    @PutMapping("/addUser")
    public R addUser(@RequestBody SysUser user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        user.setHeadUrl("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        boolean b = sysUserService.save(user);
        return b ? R.ok() : R.fail();
    }
}
