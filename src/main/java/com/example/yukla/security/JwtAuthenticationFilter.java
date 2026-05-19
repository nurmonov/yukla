package com.example.yukla.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        log.info("🔍 Filter ishlamoqda. Path: {}", path);

        String authHeader = request.getHeader("Authorization");
        log.info("📌 Authorization Header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("⏭️ Token yo'q yoki noto'g'ri format - filter o'tkazib yuborildi");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7).trim();
        log.info("🔑 Extracted Token: {}", token.substring(0, Math.min(token.length(), 50)) + "...");

        try {
            String username = jwtUtil.extractUsername(token);
            log.info("👤 Extracted Username: {}", username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                log.info("✅ UserDetails topildi: {}", userDetails.getUsername());

                boolean isValid = jwtUtil.validateToken(token, userDetails);
                log.info("✅ Token validmi? {}", isValid);

                if (isValid) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("🎉 Muvaffaqiyatli autentifikatsiya: {}", username);
                } else {
                    log.warn("❌ Token valid emas!");
                }
            }
        } catch (Exception e) {
            log.error("🚨 Filterda katta xato: {}", e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }
    // FAQAT login va register ni filterdan chetlab o'tkazamiz
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/auth/register") ||
                path.startsWith("/api/auth/login") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs");
    }
}