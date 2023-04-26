package com.zyl.system.controller;

import com.zyl.common.result.ResultCodeEnum;
import com.zyl.common.util.Salt;
import com.zyl.model.system.SysUser;
import com.zyl.system.exception.MyException;
import com.zyl.system.service.SysUserService;
import org.springframework.util.StringUtils;
import com.zyl.common.result.R;
import com.zyl.model.vo.LoginVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public R login(@RequestBody LoginVo loginVo) {

        return userService.login(loginVo);

    }
}
