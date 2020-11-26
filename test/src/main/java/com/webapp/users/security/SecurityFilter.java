package com.webapp.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class SecurityFilter extends GenericFilterBean {
        private final static String urlAuth = "http://45.12.237.140:8080/auth";
//    private final static String urlAuth = "http://localhost:8080/auth";
    private final static String authorization = "Authorization";
    RestTemplate restTemplate;

    @Autowired
    public SecurityFilter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpHeaders httpHeaders = new HttpHeaders();

        if (request.getHeader(authorization) != null) {
            ResponseEntity<AuthenticationImpl> resp;

            httpHeaders.add(authorization, request.getHeader(authorization));
            HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

            resp = restTemplate.exchange(urlAuth, HttpMethod.GET, httpEntity, AuthenticationImpl.class);
            SecurityContextHolder.setContext(new SecurityContextImpl(resp.getBody()));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
