package com.wj.jwt.handler;

import cn.hutool.core.util.StrUtil;
import com.wj.jwt.annotation.IgnoreToken;
import com.wj.jwt.service.JwtService;
import com.wj.jwt.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.lang.reflect.Method;

@Slf4j
public class AuthorisationInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Value("${spring.profiles.active}")
    private String profiles;

    private static final String AUTH = "Authorization";
    private static final String AUTH_USERNAME = "username";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("执行了 AuthorisationInterceptor 的 preHandle 方法");

        //1-过滤开发环境，开发环境不需要验证token
         if (!StrUtil.isBlank(profiles) && "dev".equals(profiles) ){
             return true;
         }
        //2-ignoreToken，不需要验证 token
        if (ignoreToken((HandlerMethod) handler)) return true;

        //3- 获取 token
        String token = getParamValue(request, AUTH);

        //4- 获取并验证 username
        String username = getParamValue(request, AUTH_USERNAME);
        ResponseResult responseResult = jwtService.verifyUsername(token, username);
        if (responseResult.getStatus()!=1){
            log.error("用户名校验失败");
            throw new ValidationException("300","用户名校验失败！");
        }
        //这里需要注意：
        // 1）如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        // 2）如果设置为true时，请求将会继续执行后面的操作
        return true;


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("执行了 AuthorisationInterceptor 的 postHandle 方法");


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("执行了 AuthorisationInterceptor 的 afterCompletion 方法");
    }


    private String getParamValue(HttpServletRequest request, String filed) throws ValidationException {
        String value = getParam(request,filed);

        if (StrUtil.isEmpty(value)){
            throw new ValidationException("300",filed+"不允许为空，请重新登录！");
        }

        return value;
    }

    /**
     * 获取参数的值 -- 若参数中不存在，则从请求头中获取
     * @param request 请求
     * @param filedName 参数名称
     * @return
     */
    private static String getParam(HttpServletRequest request,String filedName){
       String param = request.getParameter(filedName);
       if (StrUtil.isEmpty(param)){
           param = request.getHeader(filedName);
       }
       return param;
    }

    /**
     * 忽略 token 的处理
     * @param handler
     * @return
     */
    private boolean ignoreToken(HandlerMethod handler) {
        Method method = handler.getMethod();
        if (method.isAnnotationPresent(IgnoreToken.class)){
            IgnoreToken ignoreToken = method.getAnnotation(IgnoreToken.class);
            return ignoreToken.required();
        }
        return false;
    }
}

