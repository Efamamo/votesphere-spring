package com.itsc.votesphere.filter;

import org.springframework.stereotype.Component;

import com.itsc.votesphere.services.JwtUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, java.io.IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = null;

        Cookie[] cookies = httpRequest.getCookies();
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null || !JwtUtil.validateToken(token)) {
            httpResponse.sendRedirect("/auth/login"); 
            return;
        }
        

        httpRequest.setAttribute("user", JwtUtil.getClaims(token));
        chain.doFilter(request, response);
    }
}
