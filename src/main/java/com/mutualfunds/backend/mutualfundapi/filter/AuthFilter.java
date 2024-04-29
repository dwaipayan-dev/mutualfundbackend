package com.mutualfunds.backend.mutualfundapi.filter;

import com.mutualfunds.backend.mutualfundapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private static final String PHONE_NUMBER_HEADER = "phoneNumber";

    private final UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if(!httpServletRequest.getRequestURL().toString().contains("/h2console")){
            String phoneNumber = httpServletRequest.getHeader(PHONE_NUMBER_HEADER);
            if(Objects.isNull(phoneNumber)){
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Mandatory header 'phoneNumber' missing.");
            }
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            userService
                                    .getUserWithPhoneNumber(Long.valueOf(phoneNumber)),
                            null
                    )
            );
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
