package com.example.saferide.config;
import com.example.saferide.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomPreFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String author = request.getHeader("Authorization");
        if (author == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (author.isBlank() || !author.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = author.substring("Bearer ".length());
        try {
            String username = jwtService.extract(token);
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
            Boolean checkValid = jwtService.isValid(token, userDetails);
            if (checkValid == true) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                securityContext.setAuthentication(usernamePasswordAuthenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            response.setStatus(401);
            return;
        }
        filterChain.doFilter(request, response);
    }

}
