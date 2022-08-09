package com.wj.springSecurity.jwt.config;

import com.wj.springSecurity.jwt.entity.Permission;
import com.wj.springSecurity.jwt.filter.JwtAuthenticationFilter;
import com.wj.springSecurity.jwt.reposity.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.List;

/**
 * 新版本 Spring Security 的认证权限配置类写法
 */
@Configuration
@Slf4j
public class SecurityConfig {

    @Resource
    private PermissionMapper permissionMapper;
    //JWT 的认证过滤器
    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                authorizeRequests = http.csrf().disable().authorizeRequests();
//         1.查询到所有的权限
        List<Permission> allPermission = permissionMapper.findAllPermission();
//         2.分别添加权限规则
        allPermission.forEach((p -> {
            authorizeRequests.antMatchers(p.getUrl()).hasAnyAuthority(p.getName()) ;
        }));

        authorizeRequests.and()
                // 配置为 Spring Security 不创建使用 session
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authorizeRequests()
                .antMatchers("/**").fullyAuthenticated()
                .antMatchers("/authenticate").permitAll()
               .anyRequest().authenticated();
        //配置认证过滤器 jwtAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 资源放行配置
     * @return
     */
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring().antMatchers("/hello");
            web.ignoring().antMatchers("/login");
            //获取 token 的接口放行
            web.ignoring().antMatchers("/authenticate");
            web.ignoring().antMatchers("/css/**", "/js/**");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
