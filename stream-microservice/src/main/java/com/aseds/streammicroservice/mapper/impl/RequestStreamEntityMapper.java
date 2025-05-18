package com.aseds.streammicroservice.mapper.impl;

import com.aseds.streammicroservice.mapper.Mapper;
import com.aseds.streammicroservice.model.StreamEntity;
import com.aseds.streammicroservice.model.dto.StreamRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestStreamEntityMapper implements Mapper<StreamRequest, StreamEntity> {
    private final ModelMapper modelMapper;

    @Autowired
    public RequestStreamEntityMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public StreamRequest mapTo(StreamEntity streamEntity) {
        return modelMapper.map(streamEntity, StreamRequest.class);
    }

    @Override
    public StreamEntity mapFrom(StreamRequest streamRequest) {
        return modelMapper.map(streamRequest, StreamEntity.class);
    }
}
