package com.zyl.system.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyl.common.result.R;
import com.zyl.common.util.Salt;
import com.zyl.model.system.SysUser;
import com.zyl.model.vo.SysUserQueryVo;
import com.zyl.system.mapper.SysUserMapper;
import com.zyl.system.service.SysUserService;
import com.zyl.system.util.ExcelListener;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zyl
 * @date 2023/4/26 18:17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    private final SysUserService userService;
    private final SysUserMapper sysUserMapper;

    /**
     * 更新用户状态
     *
     * @param userId userId
     * @param status 状态
     * @return R
     */
    @PutMapping("/updateStatus/{userId}/{status}")
    public R updateStatus(@PathVariable Long userId, @PathVariable Integer status) {
        return userService.updateStatus(userId, status) ? R.ok() : R.fail();
    }

    /**
     * 根据id删除用户（逻辑删除）
     *
     * @param userId userId
     * @return R
     */
    @DeleteMapping("/deleteUser/{userId}")
    public R deleteUser(@PathVariable Long userId) {
        return userService.removeById(userId) ? R.ok() : R.fail();
    }

    /**
     * 根据id查询用户
     *
     * @param userId userId
     * @return R
     */
    @GetMapping("/getUser/{userId}")
    public R getUser(@PathVariable Long userId) {
        return R.ok(userService.getById(userId));
    }

    /**
     * 根据id更新用户
     *
     * @param user user
     * @return R
     */
    @PutMapping("/update")
    public R updateUser(@RequestBody SysUser user) {
        user.setUpdateTime(null);
        return userService.updateById(user) ? R.ok() : R.fail();
    }

    /**
     * 添加用户
     *
     * @param user user
     * @return R
     */
    @PostMapping("/save")
    public R addUser(@RequestBody SysUser user) {
        user.setPassword(Salt.encrypt(user.getPassword()));
        user.setHeadUrl("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return userService.save(user) ? R.ok() : R.fail();
    }

    @GetMapping("/info")
    public R info(String token) {
        System.out.println(token);
        Map<String, Object> res = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        list.add("admin");

        res.put("roles", list);
        res.put("introduction", "I am a super administrator");
        res.put("avatar", "https://img1.baidu.com/it/u=4142578214,2550299779&fm=253&fmt=auto&app=138&f=GIF?w=530&h=500");
        res.put("name", "Super Admin");
        return R.ok(res);
    }

    @PostMapping("page/{pageNum}/{size}")
    public R<IPage<SysUser>> update(@PathVariable Integer pageNum, @PathVariable Integer size, @RequestBody SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> pageParam = new Page<>(pageNum, size);
        IPage<SysUser> pageModel = userService.selectPage(pageParam, sysUserQueryVo);
        return R.ok(pageModel);
    }

    /**
     * 导入用户
     * @param myFile excel文件
     */
    @PostMapping("/batchInsert")
    public R batchInsert(@RequestParam ("file") MultipartFile myFile) {
        try (InputStream is = myFile.getInputStream()) {
            EasyExcel.read(is, SysUser.class, new ExcelListener(sysUserMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok();
    }
}
