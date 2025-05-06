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
public class service_channel_subscriptions {

    @Autowired
    private Channel_repository repository;
    @Autowired
    private Mapper mapper;

    public void subscribed(int idChannel, int id_user){
        ChannelEntity channel=this.repository.findById(idChannel)
                .orElseThrow(()->new IllegalArgumentException("CHANNEL NOT FOUND FOR ID"+idChannel));
        channel.getSubscriptions().add(id_user);
        this.repository.save(channel);
    }

    public void unsubscribed(int idChannel, int id_user){
        ChannelEntity channel=this.repository.findById(idChannel)
                .orElseThrow(()->new IllegalArgumentException("CHANNEL NOT FOUND FOR ID"+idChannel));
        channel.getSubscriptions().remove(id_user);
        this.repository.save(channel);

    }
    public List<Channel_dto> channelsSubscribed(int id){
        return this.repository.findAllBySubscriberId(id)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<Channel_dto> getSubscribedChannels(int id){
        return this.repository.findAllBySubscriberId(id)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
