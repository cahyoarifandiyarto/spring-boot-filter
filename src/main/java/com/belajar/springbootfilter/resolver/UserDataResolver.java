package com.belajar.springbootfilter.resolver;

import com.belajar.springbootfilter.entity.User;
import com.belajar.springbootfilter.exception.ErrorException;
import com.belajar.springbootfilter.mapper.UserMapper;
import com.belajar.springbootfilter.model.UserData;
import com.belajar.springbootfilter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDataResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserData.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Long id = (Long) webRequest.getAttribute("id", 0);

        if (id == null) {
            throw new ErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ErrorException(HttpStatus.UNAUTHORIZED));

        return userMapper.userToUserData(user);
    }

}
