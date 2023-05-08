package com.zyl.system;

import com.alibaba.excel.EasyExcel;
import com.zyl.common.util.SHA256;
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
    public void test2(){
        System.out.println(SHA256.encrypt("123456"));
        System.out.println(SHA256.verify("123456", "10000:13e991b0f56d1fb303728848e097fb625714d3608747a7eab4f507c139cfa4da:613acdf04275bb218340fb515e70bdd8c78e56914b52359d4d0ef9be97150e89"));
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
