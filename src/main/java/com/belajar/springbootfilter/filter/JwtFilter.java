package com.belajar.springbootfilter.filter;

import com.belajar.springbootfilter.model.Response;
import com.belajar.springbootfilter.repository.UserRepository;
import com.belajar.springbootfilter.util.ResponseUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    private final ObjectMapper objectMapper;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authorization == null || !authorization.startsWith("Bearer")) {
                Response<Void> resp = ResponseUtil.build(null, null, null, HttpStatus.UNAUTHORIZED);

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(objectMapper.writeValueAsString(resp));

                return;
            }

            SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));

            String jwt = authorization.split("Bearer ")[1];

            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwt);

            Map<String, Object> data = objectMapper.convertValue(jws.getPayload().get("data"), new TypeReference<>() {});

            Long id = ((Number) data.get("id")).longValue();

            request.setAttribute("id", id);

            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            Response<Void> resp = ResponseUtil.build(null, null, null, HttpStatus.UNAUTHORIZED);

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(resp));
        }
    }

}
