package com.newegg.autopricing.angular.web.security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 攔截未登入的使用者，並將Principal轉為User並加入request attribute
 * 
 * @author lw69
 */
public class UserPrincipalInterceptor extends HandlerInterceptorAdapter {

    private static final Logger L = LoggerFactory
            .getLogger(UserPrincipalInterceptor.class);

    public static final String ATTR_NAME_FOR_USER = "_LOGIN_USER_";

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            L.info("Unauthorized request from " + request.getRemoteHost());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        String domainUser = principal.getName();
        L.info("UserPrincipal: " + domainUser);
        if ("ABS_CORP\\rdimservice".equals(domainUser)) {
            L.info("Unauthorized request from " + request.getRemoteHost());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        int domainLength = domainUser.indexOf('\\');
        String domain = domainUser.substring(0, domainLength);
        String name = domainUser.substring(domainLength + 1);
        User user = new User(name, domain);
        request.setAttribute(ATTR_NAME_FOR_USER, user);

        return true;
    }

}
