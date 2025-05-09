package com.aseds.channelsmicroservice.Services;


import com.aseds.channelsmicroservice.Mappers.Mapper;
import com.aseds.channelsmicroservice.models.ChannelEntity;
import com.aseds.channelsmicroservice.models.dto.Channel_dto;
import com.aseds.channelsmicroservice.repositories.Channel_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class service_channel_management {
    @Autowired
    private Channel_repository repository;
    @Autowired
    private Mapper mapper;

    public List<Channel_dto> getChannels(){
        return this.repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

    }

    public Channel_dto getChannelById(int id){
        return this.repository
                .findById(id)
                .map(mapper::toDto)
                .orElseThrow(()-> new IllegalArgumentException("CHANNEL NOT FOUND FOR ID "+id));
    }
    public Channel_dto getChannelByOwnerId(int id) {
        return this.repository
                .findByOwnerId(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("CHANNEL NOT FOUND FOR USER_ID " + id));
    }

    public Channel_dto updateChannel(int id,Channel_dto channel_dto){
        ChannelEntity channel =this.repository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("CHANNEL NOT FOUND FOR ID "+id));
        this.mapper.updateEntityFromDto(channel,channel_dto);
        return this.mapper.toDto(this.repository.save(channel));
    }

    public Channel_dto createChannel(Channel_dto channelDto) {
        ChannelEntity created=this.repository.save(this.mapper
                .toEntity(channelDto));
        return mapper.toDto(created);
    }
    public void deleteChannel(int id){
        this.repository.deleteById(id);
    }
    public boolean isChannelExist(int id){
        return this.repository.findById(id).isPresent();
    }
}
