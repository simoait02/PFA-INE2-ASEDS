package com.aseds.videomicroservice.mapper.impl;

import com.aseds.videomicroservice.mapper.Mapper;
import com.aseds.videomicroservice.model.Video;
import com.aseds.videomicroservice.model.dto.VideoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoMapperDTO implements Mapper<VideoDTO, Video> {
    private final ModelMapper modelMapper;

    @Autowired
    public VideoMapperDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public VideoDTO mapTo(Video video) {
        return modelMapper.map(video, VideoDTO.class);
    }

    @Override
    public Video mapFrom(VideoDTO videoDTO) {
        return modelMapper.map(videoDTO, Video.class);
    }
}
