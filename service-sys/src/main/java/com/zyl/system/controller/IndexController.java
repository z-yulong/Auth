package com.zyl.system.controller;

import com.zyl.common.result.R;
import com.zyl.model.system.SysUser;
import com.zyl.system.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: zyl
 * @date 2023/4/26 20:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/system/index")
public class IndexController {
    private final SysUserService userService;
    private final RedisTemplate redisTemplate;


    /**
     * 登出
     */
    @PostMapping("/logout")
    public R<Map<String,Object>> logout(@RequestHeader String token) {
        return userService.logout(token);
    }

    @GetMapping("/info")
    public R getInfo(@RequestHeader String token){
        System.out.println("token = " + token);

        Object o = redisTemplate.boundValueOps(token).get();
        System.out.println("o = " + o);
        SysUser sysUser = (SysUser)redisTemplate.boundValueOps(token).get();
        //调用SysUserService中根据用户id获取用户信息的方法
        Map<String,Object> map = userService.getUserInfoByUserId(sysUser.getId());
        return R.ok(map);
    }
}
