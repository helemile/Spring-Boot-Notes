package com.wj.springSecurity.config;

import com.wj.springSecurity.entity.User;
import com.wj.springSecurity.reposity.PermissionMapper;
import com.wj.springSecurity.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserService<User> userService;

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 将用户配置在内存或者mysql中（可登录用户，包括用户名、密码和角色）
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 方式一：写到内存
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("123456")).authorities("admin")
                .and()
                .withUser("user").password(passwordEncoder().encode("123456")).authorities("common");
       // 方式二：来源于数据库
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());

        //        System.out.println("密码："+new BCryptPasswordEncoder().encode("123456"));
    }

    /**
     * 登录处理-配置对应用户所拥有的权限(url、authority对应的所有关系)
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        方式一：写死的方式
                http.authorizeRequests().
        antMatchers("/user/common").hasAnyAuthority("admin")
                .antMatchers("/user/admin").hasAnyAuthority("common")
                .antMatchers("/user/common").hasAnyAuthority("common")
                .antMatchers("/**").fullyAuthenticated()
                .anyRequest().authenticated()
                .and().formLogin();

//        方式二：配置来源于数据库
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
//                authorizeRequests = http.csrf().disable().authorizeRequests();
        // 1.查询到所有的权限
//        List<Permission> allPermission = permissionMapper.findAllPermission();
        // 2.分别添加权限规则
//        allPermission.forEach((p -> {
//            authorizeRequests.antMatchers(p.getUrl()).hasAnyAuthority(p.getName()) ;
//        }));
//
//        authorizeRequests.antMatchers("/**").fullyAuthenticated()
//                .anyRequest().authenticated().and().formLogin();


    }

    /**
     * 忽略拦截处理
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略url - 会直接过滤该url - 将不会经过Spring Security过滤器链
        web.ignoring().antMatchers("/getUserInfo");
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    /**
     *  密码解密类
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
