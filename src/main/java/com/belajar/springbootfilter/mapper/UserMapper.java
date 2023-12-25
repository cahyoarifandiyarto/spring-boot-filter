package com.belajar.springbootfilter.mapper;

import com.belajar.springbootfilter.entity.User;
import com.belajar.springbootfilter.model.RegisterRequest;
import com.belajar.springbootfilter.model.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User registerRequestToUser(RegisterRequest registerRequest);

    UserData userToUserData(User user);

}
