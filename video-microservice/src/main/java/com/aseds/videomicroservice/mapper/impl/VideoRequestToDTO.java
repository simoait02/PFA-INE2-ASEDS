package com.aseds.videomicroservice.mapper.impl;

import com.aseds.videomicroservice.mapper.Mapper;
import com.aseds.videomicroservice.model.dto.VideoDTO;
import com.aseds.videomicroservice.model.dto.VideoRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoRequestToDTO implements Mapper<VideoRequest, VideoDTO> {
    private final ModelMapper modelMapper;

    @Autowired
    public VideoRequestToDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public VideoRequest mapTo(VideoDTO videoDTO) {
        return modelMapper.map(videoDTO, VideoRequest.class);
    }

    @Override
    public VideoDTO mapFrom(VideoRequest videoRequest) {
        return modelMapper.map(videoRequest, VideoDTO.class);
    }
}
