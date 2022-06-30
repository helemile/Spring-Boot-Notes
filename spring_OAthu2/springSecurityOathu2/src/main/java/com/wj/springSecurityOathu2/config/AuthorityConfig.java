package com.wj.springSecurityOathu2.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 认证授权的 server 端
 */
@Component
@EnableAuthorizationServer
public class AuthorityConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private DataSource dataSource;

    /**
     * 允许表单模式的认证
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");
    }

    /**
     * 配置所有客户端的信息（client_id、secret、授权模式、scope、资源id 以及重定向地址）
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         * 方法一：内存配置
         */
//        clients.inMemory()
//                .withClient("user-client")
//                .secret(passwordEncoder.encode("123456"))
//                .authorizedGrantTypes("authorization_code")
//                .scopes("all")
//                .resourceIds("wj_resource")
//                .redirectUris("http://localhost:8082/user/hello");

        /**
         * 方法二：db配置
         */
        JdbcClientDetailsServiceBuilder jcsb = clients.jdbc(dataSource);
        jcsb.passwordEncoder(passwordEncoder);

    }
}
