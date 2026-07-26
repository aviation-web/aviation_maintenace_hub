package com.aeromaintenance.Config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class LocationContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String location = httpRequest.getHeader("X-User-Location");

        if (location != null && !location.isEmpty()) {
            LocationBasedRoutingDataSourceContextHolder.setLocation(location.toLowerCase());
        }

        try {
            chain.doFilter(request, response);
        } finally {
            LocationBasedRoutingDataSourceContextHolder.clear(); // Prevent memory leaks
        }
    }

	
}