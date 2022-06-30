package com.wj.resource_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class ResourceConfig  extends ResourceServerConfigurerAdapter {

    @Value("${wj.appid}")
    private String wjAppId;

    @Value("${wj.appsecret}")
    private String wjAppSecret;

    /**
     *  密码解密类
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 校验 token 的正确性
     * @return
     */
    @Bean
    @Primary
    public RemoteTokenServices remoteTokenServices(){

        final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setClientId(wjAppId);
        remoteTokenServices.setClientSecret(wjAppSecret);
        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:8082/oauth/check_token");
        return remoteTokenServices;

    }

    /**
     *  定义资源id，以及无状态声明
     * @param resources
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("wj_resource").stateless(true);
    }

    /**
     * 设置 所有请求必须先授权之后再进行请求
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //设置创建session策略
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        //@formatter:off
        //所有请求必须授权
        http.authorizeRequests()
                .anyRequest().authenticated();
        //@formatter:on
    }
}
