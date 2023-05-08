package com.zyl.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyl.common.result.R;
import com.zyl.common.result.ResultCode;
import com.zyl.common.util.JWTUtil;
import com.zyl.common.util.Salt;
import com.zyl.model.system.SysUser;
import com.zyl.model.vo.LoginVo;
import com.zyl.model.vo.SysUserQueryVo;
import com.zyl.system.exception.MyException;
import com.zyl.system.mapper.SysUserMapper;
import com.zyl.system.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * author: zyl
 * <br/>
 * date 2023/4/26 18:12
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final RedisTemplate<String,Object> redisTemplate;
    private final SysUserMapper userMapper;

    /**
     * 更新用户状态
     *
     * @param id     userId
     * @param status 状态
     * @return R
     */
    public boolean updateStatus(Long id, Integer status) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setStatus(status);
        return baseMapper.updateById(sysUser) != 0;
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return SysUser
     */
    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(username), SysUser::getUsername, username);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 登录
     */
    @Override
    public R<Map<String, Object>> login(LoginVo loginVo) {
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        //参数是否拿到
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new MyException(ResultCode.USERNAME_PASSWORD_NULL);
        }
        //根据用户名查询用户
        SysUser user = this.getUserByUsername(username);
        //根据username是否查询到一个user
        if (null == user) {
            throw new MyException(ResultCode.ACCOUNT_ERROR);
        }
        //验证密码
        if (!Salt.verify(password, user.getPassword())) {
            throw new MyException(ResultCode.PASSWORD_ERROR);
        }
        //判断用户是否被禁用
        if (user.getStatus() == 0) {
            throw new MyException(ResultCode.ACCOUNT_STOP);
        }
        //生成token
        String token = JWTUtil.createToken(user);
        //将用户存入redis，有效期5小时
        redisTemplate.boundValueOps(token).set(user, 5, TimeUnit.HOURS);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user);
        return R.ok(map);
    }


    @Override
    public R<Map<String, Object>> logout(String token) {
        Boolean b = redisTemplate.delete(token);
        return b ? R.ok() : R.fail();
    }

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo) {
        return userMapper.selectPage(pageParam,sysUserQueryVo);
    }


}
