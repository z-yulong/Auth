package com.zyl.system.filter;

/**
 * @author: zyl
 * @date 2023/5/8 20:37
 */

import com.zyl.common.result.R;
import com.zyl.common.result.ResultCode;
import com.zyl.common.util.ResponseUtil;
import com.zyl.model.system.SysUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 认证解析token过滤器
 * </p>
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("uri:"+request.getRequestURI());
        //如果是登录接口，直接放行
        if("/admin/system/index/login".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if(null != authentication) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //放行请求
            chain.doFilter(request, response);
        } else {
            ResponseUtil.out(response, R.build(null, ResultCode.PERMISSION));
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        String token = request.getHeader("token");

        if (!StringUtils.isEmpty(token)) {
            SysUser sysUser = (SysUser)redisTemplate.boundValueOps(token).get();
            if (null != sysUser) {
                if (null != sysUser.getUserPermsList() && sysUser.getUserPermsList().size() > 0) {
                    List<SimpleGrantedAuthority> authorities = sysUser.getUserPermsList().stream().filter(code -> !StringUtils.isEmpty(code.trim())).map(code -> new SimpleGrantedAuthority(code.trim())).collect(Collectors.toList());
                    //返回一个认证之后包含权限的对象
                    return new UsernamePasswordAuthenticationToken(sysUser.getUsername(), null, authorities);
                } else {
                    //返回一个认证之后没有权限的对象
                    return new UsernamePasswordAuthenticationToken(sysUser.getUsername(), null, Collections.emptyList());
                }
            }
        }
        return null;
    }
}
