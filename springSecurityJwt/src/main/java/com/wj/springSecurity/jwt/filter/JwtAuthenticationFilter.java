package com.wj.springSecurity.jwt.filter;


import cn.hutool.core.util.StrUtil;
import com.wj.springSecurity.jwt.service.JwtService;
import com.wj.springSecurity.jwt.service.UserService;
import com.wj.springSecurity.jwt.vo.ResponseResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 认证过滤器
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            //1-获取 token
            String token = request.getHeader("Authorization");
            if (StrUtil.isBlank(token)) {
                //放行，会自动执行后面的过滤器
                logger.info("请求头不含 JWT token 或者 token 的值为空，调用下个过滤器");
                filterChain.doFilter(request,response);
                return;
            }
            //2-获取认证信息
            Authentication authentication = jwtService.getAuthentication(token);
            //3-设置用户验证对象
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
        } finally{
            filterChain.doFilter(request, response);
        }
    }


}