package com.example.demo.core;

import com.example.demo.application.UserSecurityService;
import com.example.demo.domain.models.User;
import com.example.demo.infrastructure.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final UserSecurityService userSecurityService;

    @Autowired
    private final UserRepository userRepository;

    public JwtFilter(JwtUtil jwtUtil, UserSecurityService userSecurityService, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userSecurityService = userSecurityService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);


        if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
            System.out.println("No JWT token found in the request header");
            filterChain.doFilter(request, response);
            return;
        }


        String jwt = header.split(" ")[1].trim();



        if (!this.jwtUtil.isValid(jwt)) {

            filterChain.doFilter(request, response);
            return;
        }


        String emailUser = this.jwtUtil.getEmail(jwt);



        Optional<User> userInDb = this.userRepository.findByEmail(emailUser);
        if (userInDb.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access denied or user not found");
            return;
        }


        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) this.userSecurityService.loadUserByUsername(emailUser);



        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        filterChain.doFilter(request, response);

    }
}