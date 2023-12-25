package com.belajar.springbootfilter.controller;

import com.belajar.springbootfilter.model.LoginRequest;
import com.belajar.springbootfilter.model.LoginResponse;
import com.belajar.springbootfilter.model.Response;
import com.belajar.springbootfilter.service.LoginService;
import com.belajar.springbootfilter.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<Response<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse loginResponse = loginService.login(request);

        Response<LoginResponse> response = ResponseUtil.build(loginResponse, null, null, HttpStatus.OK);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
