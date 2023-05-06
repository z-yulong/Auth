package com.zyl.system.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zyl.common.util.Salt;
import com.zyl.model.system.SysUser;
import com.zyl.system.mapper.SysUserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zyl
 * @date 2023/5/5 15:31
 */

public class ExcelListener extends AnalysisEventListener<SysUser> {

    private final SysUserMapper userMapper;
    public ExcelListener(SysUserMapper userMapper) {
        this.userMapper = userMapper;
    }
    List<SysUser> users = new ArrayList<>();

    @Override
    public void invoke(SysUser user, AnalysisContext context) {
        user.setStatus(1);
        user.setHeadUrl("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        user.setPassword(Salt.encrypt(user.getPassword()));
        users.add(user);
        if (users.size() == 1024) {
            this.saveData();
            users.clear();
        }
    }

    //插入
    private void saveData() {
        userMapper.batchInsert(users);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        this.saveData();
    }
}
