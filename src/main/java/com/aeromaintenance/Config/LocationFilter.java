package com.aeromaintenance.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LocationFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String location = request.getHeader("X-User-Location");
        if (location != null) {
            LocationBasedRoutingDataSourceContextHolder.setLocation(location.toLowerCase());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LocationBasedRoutingDataSourceContextHolder.clear();
    }
}
