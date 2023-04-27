package com.zyl.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyl.model.system.SysRole;
import com.zyl.model.system.SysUser;
import com.zyl.model.system.SysUserRole;
import com.zyl.system.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: zyl
 * @date 2023/4/26 16:25
 */
@SpringBootTest
public class test {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Test
    public void test(){
        List<SysUser> sysUsers = sysUserMapper.selectList(null);

        sysUsers.forEach(System.out::println);
    }

}
