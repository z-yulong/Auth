package com.zyl.system.filter;

/**
 * @author: zyl
 * @date 2023/5/8 20:28
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyl.common.result.R;
import com.zyl.common.util.JWTUtil;
import com.zyl.common.util.ResponseUtil;
import com.zyl.model.system.SysUser;
import com.zyl.model.vo.LoginVo;
import com.zyl.system.custom.CustomUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 * </p>
 *
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final RedisTemplate redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        this.setAuthenticationManager(authenticationManager);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login","POST"));
        //给redisTemplate属性赋值
        this.redisTemplate = redisTemplate;
    }

    /**
     * 登录认证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            LoginVo loginVo = new ObjectMapper().readValue(req.getInputStream(), LoginVo.class);

            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 登录成功
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        SysUser sysUser = customUser.getSysUser();
        String token = JWTUtil.createToken(sysUser);
        redisTemplate.boundValueOps(token).set(customUser.getSysUser(),2, TimeUnit.HOURS);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        ResponseUtil.out(response, R.ok(map));
    }

    /**
     * 登录失败
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response,R.build(null,444,failed.getMessage()));
    }
}