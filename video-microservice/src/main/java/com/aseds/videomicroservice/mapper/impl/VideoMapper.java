package com.aseds.videomicroservice.mapper.impl;

import com.aseds.videomicroservice.mapper.Mapper;
import com.aseds.videomicroservice.model.Video;
import com.aseds.videomicroservice.model.dto.VideoRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper implements Mapper<Video, VideoRequest> {
    private final ModelMapper modelMapper;

    @Autowired
    public VideoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Video mapTo(VideoRequest videoRequest) {
        return modelMapper.map(videoRequest, Video.class);
    }

    @Override
    public VideoRequest mapFrom(Video video) {
        return modelMapper.map(video, VideoRequest.class);
    }
}
