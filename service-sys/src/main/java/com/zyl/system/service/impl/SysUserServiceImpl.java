package com.zyl.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyl.common.result.R;
import com.zyl.common.result.ResultCodeEnum;
import com.zyl.common.util.Salt;
import com.zyl.model.system.SysUser;
import com.zyl.model.vo.LoginVo;
import com.zyl.system.exception.MyException;
import com.zyl.system.mapper.SysUserMapper;
import com.zyl.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: zyl
 * @date 2023/4/26 18:12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final RedisTemplate redisTemplate;
    public SysUserServiceImpl(SysUserMapper sysUserMapper, RedisTemplate redisTemplate) {
        this.sysUserMapper = sysUserMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 更新用户状态
     * @param id userId
     * @param status 状态
     * @return R
     */
    public boolean updateStatus(Long id, Integer status) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setStatus(status);
        int i = baseMapper.updateById(sysUser);
        return i != 0;
    }

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return SysUser
     */
    @Override
    public SysUser getUserByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<SysUser>().eq("username",username));
    }

    @Override
    public R login(LoginVo loginVo) {
        String username=loginVo.getUsername();
        String password=loginVo.getPassword();
        //参数是否拿到
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new MyException(444,"用户名或密码不能为空！");
        }
        SysUser user = this.getUserByUsername(username);
        //根据username是否查询到一个user
        if(null==user){
            return R.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }
        //验证密码
        if(!Salt.verify(password,user.getPassword())){
            return R.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }
        //判断用户是否被禁用
        if(user.getStatus() == 0){
            return R.build(null, ResultCodeEnum.ACCOUNT_STOP);
        }
        // UUID 生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //将用户存入redis，有效期2小时
        redisTemplate.boundValueOps(token).set(user,2, TimeUnit.HOURS);
        Map<String,Object> map = new HashMap<>();
        map.put("token" ,token);
        map.put("user",user);

        return R.ok(map);
    }
}
