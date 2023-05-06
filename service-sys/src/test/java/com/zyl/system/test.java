package com.zyl.system;

import com.alibaba.excel.EasyExcel;
import com.zyl.model.system.SysUser;
import com.zyl.system.mapper.SysUserMapper;
import com.zyl.system.util.ExcelListener;
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
    @Test
    public void test1(){
        //设置文件名称和路径
        String fileName="d:\\Desktop\\01.xlsx";
        //调用方法进行读操作
        EasyExcel.read(fileName,SysUser.class,new ExcelListener(sysUserMapper))
                .sheet()
                .doRead();
    }



}
