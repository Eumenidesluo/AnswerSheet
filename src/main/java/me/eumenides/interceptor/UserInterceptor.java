package me.eumenides.interceptor;

import me.eumenides.utils.UnLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

import static me.eumenides.interceptor.Constants.SESSION_KEY;
import static me.eumenides.interceptor.Constants.SESSION_KEY_PREFIX;
import static me.eumenides.interceptor.Constants.USER_CODE_SESSION_KEY;

/**
 * Created by Eumenides on 2017/11/30.
 */
@Component
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            return true;
        }

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Method method = handlerMethod.getMethod();
        final Class<?> clazz = method.getDeclaringClass();
        if (clazz.isAnnotationPresent(Auth.class) || method.isAnnotationPresent(Auth.class)) {
            handlerSession(request);
            if (request.getAttribute(USER_CODE_SESSION_KEY) == null) {

                throw new UnLoginException();

            } else {
                return true;
            }
        }

        return true;
    }

    private void handlerSession(HttpServletRequest request) {
        HttpSession session=request.getSession();
        String sessionId = session.getId();
        if (!StringUtils.isEmpty(sessionId)) {
            Integer userCode = null;
            try {
                userCode = Integer.parseInt(session.getAttribute(SESSION_KEY).toString());
            } catch (Exception e) {
                return;
            }
            request.setAttribute(SESSION_KEY, sessionId);
            request.setAttribute(USER_CODE_SESSION_KEY, userCode);
        }
    }
}
