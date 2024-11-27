package com.hexagonal.user_auto.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        log.info("Iniciando a verificação do token JWT");
        try {
            // Exceção para as rotas /api/signin, /h2-console
            // OBS.: Apesar do anunciado informar que /api/users não precisa de autenticação, foi mantido por questoes de segurança e LGPD.
            if (request.getRequestURI().contains("/api/signin")
                    || request.getRequestURI().contains("/h2-console/")
                    || request.getRequestURI().contains("/api/cars_utilization")
                    || request.getRequestURI().contains("swagger-ui")
                    || request.getRequestURI().contains("v3/api-docs")
                    || request.getRequestURI().equals("/")
                    || request.getRequestURI().equals("/favicon.ico")) {
                chain.doFilter(request, response);
                return;
            }

            final String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Boa{\"error\": \"Unauthorized\"}");
                response.setContentType("application/json");
                return;
            }

            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtTokenUtil.extractUsername(jwt);
            }

            if (username != null) {
                logger.info("Usuário extraído do token");
            } else {
                logger.warn("Nenhum usuário encontrado no token JWT");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtTokenUtil.validateToken(jwt, userDetails.getUsername())) {
                    var authToken = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            // Limpar o contexto em caso de exceção
            SecurityContextHolder.clearContext();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
            response.setContentType("application/json");
        }
    }
}
