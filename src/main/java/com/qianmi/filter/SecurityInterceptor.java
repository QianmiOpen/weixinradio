package com.qianmi.filter;

import com.qianmi.domain.User;
import com.qianmi.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 安全拦截器
 */
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    private PathMatcher pathMatcher = new AntPathMatcher();
    private String redirectUrl;
    private List<String> excludes;

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    private boolean lookupUrl(String urlPath) {
        for (String registeredPattern : excludes) {
            if (pathMatcher.match(registeredPattern, urlPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String urlPath = request.getRequestURI();

        if (!urlPath.startsWith("/")) {
            urlPath = "/" + urlPath;
        }
        if (lookupUrl(urlPath)) {
            return true;
        }

        String username = (String) WebUtils.getSessionAttribute(request, "user");
        if (StringUtils.isEmpty(username)) {
            response.sendError(HttpStatus.FORBIDDEN.value());
            return false;
        }

        if (StringUtils.equals(username, "root")) {
            if (!StringUtils.startsWith(urlPath, "/admin")) {
                response.sendError(HttpStatus.FORBIDDEN.value());
                return false;
            } else {
                return true;
            }
        }

        User user = userService.createIfNotExists(username);
        if (StringUtils.isEmpty(user.getWxOpenId())) {
            response.sendRedirect("/user/bind");
            return false;
        }
        return true;
    }
}
