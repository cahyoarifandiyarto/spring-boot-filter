package com.belajar.springbootfilter.service;

import com.belajar.springbootfilter.model.LoginRequest;
import com.belajar.springbootfilter.model.LoginResponse;

public interface LoginService {

    LoginResponse login(LoginRequest request);

}
