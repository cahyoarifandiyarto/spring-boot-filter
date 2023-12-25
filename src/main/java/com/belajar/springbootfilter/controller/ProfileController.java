package com.belajar.springbootfilter.controller;

import com.belajar.springbootfilter.model.Response;
import com.belajar.springbootfilter.model.UserData;
import com.belajar.springbootfilter.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/profile")
public class ProfileController {

    @GetMapping
    @Operation(security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Response<UserData>> profile(@Parameter(hidden = true) UserData userData) {
        Response<UserData> response = ResponseUtil.build(userData, null, null, HttpStatus.OK);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
