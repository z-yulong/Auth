package com.zyl.system.controller;

import com.zyl.system.service.SysUserService;
import com.zyl.common.result.R;
import com.zyl.model.vo.LoginVo;
import lombok.AllArgsConstructor;
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

    /**
     * 登录
     */
    @PostMapping("/login")
    public R<Map<String,Object>> login(@RequestBody LoginVo loginVo) {
        return userService.login(loginVo);
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public R<Map<String,Object>> logout(@RequestHeader String token) {
        return userService.logout(token);
    }
}
