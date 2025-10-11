package com.portafolio.todo_simple.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 🚨 1️⃣ Ignorar rutas públicas (/auth/**)
        // Si la ruta empieza con /auth/, saltamos el filtro.
        if (request.getServletPath().startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2️⃣ Obtenemos el header Authorization del request
        String authHeader = request.getHeader("Authorization");

        // Si no hay header o no empieza con "Bearer ", seguimos sin tocar nada
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3️⃣ Extraemos el token (quitamos "Bearer ")
        String jwt = authHeader.substring(7);

        // 4️⃣ Extraemos el nombre de usuario del token
        String username = jwtService.extractUsername(jwt);

        // 5️⃣ Si el usuario es válido y no está ya autenticado en el contexto:
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 6️⃣ Validamos el token con los datos del usuario
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Si todo está bien, creamos el objeto de autenticación
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );

                // Lo metemos en el contexto de seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 7️⃣ Continuamos la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
