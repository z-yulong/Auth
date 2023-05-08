package com.zyl.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyl.common.helper.MenuHelper;
import com.zyl.common.helper.RouterHelper;
import com.zyl.common.result.R;
import com.zyl.common.result.ResultCode;
import com.zyl.common.util.JWTUtil;
import com.zyl.common.util.SHA256;
import com.zyl.model.system.SysMenu;
import com.zyl.model.system.SysUser;
import com.zyl.model.vo.LoginVo;
import com.zyl.model.vo.RouterVo;
import com.zyl.model.vo.SysUserQueryVo;
import com.zyl.system.exception.MyException;
import com.zyl.system.mapper.SysMenuMapper;
import com.zyl.system.mapper.SysUserMapper;
import com.zyl.system.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
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
    private final SysMenuMapper sysMenuMapper;

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
        if (!SHA256.verify(password, user.getPassword())) {
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

    @Override
    public Map<String, Object> getUserInfoByUserId(Long userId) {
        //查询用户信息
        SysUser sysUser = userMapper.selectById(userId);
        //调用根据用户id获取权限菜单的方法
        List<SysMenu> userMenuList = getUserMenusByUserId(userId);

        //将userMenuList转换为菜单树
        List<SysMenu> userMenuTree = MenuHelper.buildTree(userMenuList);

        //将菜单树转换为路由
        List<RouterVo> routerVoList = RouterHelper.buildRouters(userMenuTree);

        //获取用户的按钮权限标识符
        List<String> buttonPermissons = getUserBtnPermsByUserId(userId);

        //创建一个Map
        Map<String,Object> map = new HashMap<>();
        //设置前端需要的数据，{"code":200,"data":{"roles":["admin"],"introduction":"I am a super administrator","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif","name":"Super Admin"}}
        map.put("name",sysUser.getName());
        //当前权限控制使用不到，我们暂时忽略
        map.put("roles",new HashSet<>());
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("routers", routerVoList);
        map.put("buttons", buttonPermissons);
        return map;
    }

    @Override
    public List<String> getUserBtnPermsByUserId(Long userId) {
        //调用根据用户id获取权限菜单的方法
        List<SysMenu> userMenuList = getUserMenusByUserId(userId);
        //创建一个保存用户按钮权限标识的List
        List<String> buttonPermissons = new ArrayList<>();
        //遍历权限菜单
        for (SysMenu sysMenu : userMenuList) {
            //将SysMenu的type值为2的perms的值放到buttonPermissons中
            if(sysMenu.getType() == 2){
                buttonPermissons.add(sysMenu.getPerms());
            }
        }
        return buttonPermissons;
    }



    //根据用户id查询用户的权限菜单
    public List<SysMenu> getUserMenusByUserId(Long userId){
        List<SysMenu> userMenuList = null;
        //判断该用户是否是系统管理员
        if(userId == 1L){
            //证明是系统管理员
            userMenuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status",1).orderByAsc("sort_value"));
        }else{
            //根据用户id查询用户的权限菜单
            userMenuList = sysMenuMapper.selectMenuListByUserId(userId);
        }
        return userMenuList;
    }


}
