package com.belajar.springbootfilter.controller;

import com.belajar.springbootfilter.model.RegisterRequest;
import com.belajar.springbootfilter.model.Response;
import com.belajar.springbootfilter.service.RegisterService;
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
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    public ResponseEntity<Response<Void>> register(@RequestBody @Valid RegisterRequest request) {
        registerService.register(request);

        Response<Void> response = ResponseUtil.build(null, null, null, HttpStatus.CREATED);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
