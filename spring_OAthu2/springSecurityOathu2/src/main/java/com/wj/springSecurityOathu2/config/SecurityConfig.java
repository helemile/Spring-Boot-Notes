//package com.wj.springSecurityOathu2.config;
//
//import com.wj.springSecurityOathu2.entity.Permission;
//import com.wj.springSecurityOathu2.reposity.PermissionMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@Configuration
//@Slf4j
//public class SecurityConfig {
//
//    @Resource
//    private PermissionMapper permissionMapper;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
//                authorizeRequests = http.csrf().disable().authorizeRequests();
////        方式二：配置来源于数据库
////         1.查询到所有的权限
//        List<Permission> allPermission = permissionMapper.findAllPermission();
////         2.分别添加权限规则
//        allPermission.forEach((p -> {
//            authorizeRequests.antMatchers(p.getUrl()).hasAnyAuthority(p.getName()) ;
//        }));
//
//        authorizeRequests.antMatchers("/**").fullyAuthenticated()
//                .anyRequest().authenticated().and().formLogin();
//        return http.build();
//    }
//
//    @Bean
//    WebSecurityCustomizer webSecurityCustomizer() {
//        return new WebSecurityCustomizer() {
//            @Override
//            public void customize(WebSecurity web) {
//                web.ignoring().antMatchers("/hello");
//                web.ignoring().antMatchers("/css/**", "/js/**");
//            }
//        };
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//}
