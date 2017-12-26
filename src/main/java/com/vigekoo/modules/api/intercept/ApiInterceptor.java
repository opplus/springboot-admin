package com.vigekoo.modules.api.intercept;

import com.vigekoo.common.exception.AppException;
import com.vigekoo.modules.api.annotation.Login;
import io.jsonwebtoken.Claims;
import com.vigekoo.modules.api.utils.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author oplus
 * @Description: TODO(api interceptor)
 * @date 2017-9-27 14:41
 */
@Component
public class ApiInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //接口方法如果没有Login注解，则不需要校验token
        Login annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        } else {
            return true;
        }

        if (annotation == null) {
            return true;
        }

        //获取token
        String token = request.getHeader(jwtUtils.getHeader());
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(jwtUtils.getHeader());
        }

        //校验token
        if (StringUtils.isBlank(token)) {
            throw new AppException(jwtUtils.getHeader() + "不能为空", HttpStatus.UNAUTHORIZED.value());
        }
        Claims claims = jwtUtils.getClaimByToken(token);
        if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())) {
            throw new AppException(jwtUtils.getHeader() + "已经失效", HttpStatus.UNAUTHORIZED.value());
        }

        return true;
    }

}
