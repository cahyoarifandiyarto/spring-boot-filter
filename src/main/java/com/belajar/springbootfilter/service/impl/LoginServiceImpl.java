package com.belajar.springbootfilter.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.belajar.springbootfilter.entity.User;
import com.belajar.springbootfilter.exception.ErrorException;
import com.belajar.springbootfilter.model.LoginRequest;
import com.belajar.springbootfilter.model.LoginResponse;
import com.belajar.springbootfilter.repository.UserRepository;
import com.belajar.springbootfilter.service.LoginService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private final UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmailOrPhoneNumber(request.getUsername(), request.getUsername())
                .orElseThrow(() -> new ErrorException(HttpStatus.UNAUTHORIZED));

        BCrypt.Result result = BCrypt.verifyer().verify(request.getPassword().toCharArray(), user.getPassword());

        if (!result.verified) {
            throw new ErrorException(HttpStatus.UNAUTHORIZED);
        }

        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));

        Map<String, Long> data = new HashMap<>();
        data.put("id", user.getId());

        long expiration = System.currentTimeMillis() + jwtExpiration;

        String jwt = Jwts.builder()
                .expiration(new Date(expiration))
                .claim("data", data)
                .signWith(secretKey)
                .compact();

        return LoginResponse.builder()
                .accessToken(jwt)
                .build();
    }

}
