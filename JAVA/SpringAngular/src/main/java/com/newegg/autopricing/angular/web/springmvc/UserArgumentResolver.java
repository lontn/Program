package com.newegg.autopricing.angular.web.springmvc;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.newegg.autopricing.angular.web.security.User;
import com.newegg.autopricing.angular.web.security.UserPrincipalInterceptor;

/**
 * 處理Spring MVC 呼叫的 Method 參數有 @UserPrincipal User user
 * 
 * @since $Id$
 */
public class UserArgumentResolver implements WebArgumentResolver {

    private static final Logger L = LoggerFactory.getLogger(UserArgumentResolver.class);

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
            NativeWebRequest webRequest) throws Exception {

        if (methodParameter.getParameterType().equals(User.class)) {
            Annotation[] annotations = methodParameter.getParameterAnnotations();
            for (Annotation annotation : annotations) {
                if (UserPrincipal.class.isInstance(annotation)) {
                    Object arg = webRequest.getAttribute(
                            UserPrincipalInterceptor.ATTR_NAME_FOR_USER,
                            NativeWebRequest.SCOPE_REQUEST);
                    if (L.isDebugEnabled()) {
                        L.debug("Resolver User Argument: " + arg);
                    }
                    return arg;
                }
            }
        }
        return UNRESOLVED;
    }

}
