package com.aseds.usermanagementmicroservice.mappers.implementation;

import com.aseds.usermanagementmicroservice.mappers.Mapper;
import com.aseds.usermanagementmicroservice.model.UserEntity;
import com.aseds.usermanagementmicroservice.model.dto.RegisterRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<RegisterRequest, UserEntity> {
    private final ModelMapper modelMapper ;
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public RegisterRequest mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, RegisterRequest.class);
    }
    @Override
    public UserEntity mapFrom(RegisterRequest userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }
}
