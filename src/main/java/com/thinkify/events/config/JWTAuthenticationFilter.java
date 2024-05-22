package com.thinkify.events.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private static Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info(String.format("Request received with path, %s", request.getRequestURI()));
//        if(path.startsWith("/api/v1/auth")){
//            filterChain.doFilter(request, response);
//            return;
//        }
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || (!authHeader.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = authHeader.substring(7);
        LOGGER.info("jwt " + jwt);
        String userEmail = request.getHeader("email");
        LOGGER.info("user email passed in header" + userEmail);
        if(SecurityContextHolder.getContext().getAuthentication() == null) {
            if(jwtService.isTokenValid(jwt, userEmail)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                LOGGER.info("token is valid, user is authenticated");
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
