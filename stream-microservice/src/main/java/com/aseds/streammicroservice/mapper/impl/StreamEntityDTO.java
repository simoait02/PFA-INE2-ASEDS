package com.aseds.streammicroservice.mapper.impl;

import com.aseds.streammicroservice.mapper.Mapper;
import com.aseds.streammicroservice.model.StreamEntity;
import com.aseds.streammicroservice.model.dto.StreamDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StreamEntityDTO implements Mapper<StreamDTO, StreamEntity> {
    private final ModelMapper modelMapper;

    public StreamEntityDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public StreamDTO mapTo(StreamEntity streamEntity) {
        return modelMapper.map(streamEntity, StreamDTO.class);
    }

    @Override
    public StreamEntity mapFrom(StreamDTO streamDTO) {
        return modelMapper.map(streamDTO, StreamEntity.class);
    }
}
