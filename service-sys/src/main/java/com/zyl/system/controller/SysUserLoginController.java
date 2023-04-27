package com.zyl.system.controller;

import com.zyl.system.service.SysUserService;
import com.zyl.common.result.R;
import com.zyl.model.vo.LoginVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: zyl
 * @date 2023/4/26 20:16
 */
@RestController
@RequestMapping("/admin/system/index")
public class SysUserLoginController {
    private final SysUserService userService;
    public SysUserLoginController(SysUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public R<Map<String,Object>> login(@RequestBody LoginVo loginVo) {
        return userService.login(loginVo);
    }
}
