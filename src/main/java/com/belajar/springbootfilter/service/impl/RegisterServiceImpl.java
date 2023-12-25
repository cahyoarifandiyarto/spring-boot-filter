package com.belajar.springbootfilter.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.belajar.springbootfilter.entity.User;
import com.belajar.springbootfilter.exception.ErrorException;
import com.belajar.springbootfilter.mapper.UserMapper;
import com.belajar.springbootfilter.model.RegisterRequest;
import com.belajar.springbootfilter.repository.UserRepository;
import com.belajar.springbootfilter.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmailOrPhoneNumber(request.getEmail(), request.getPhoneNumber())) {
            throw new ErrorException(HttpStatus.CONFLICT);
        }

        request.setPassword(BCrypt.withDefaults().hashToString(12, request.getPassword().toCharArray()));

        User user = userMapper.registerRequestToUser(request);

        userRepository.save(user);
    }

}
