package com.aseds.channelsmicroservice.Mappers;


import com.aseds.channelsmicroservice.models.ChannelEntity;
import com.aseds.channelsmicroservice.models.dto.Channel_dto;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public Channel_dto toDto(ChannelEntity channel){
        return Channel_dto.builder()
                .id(channel.getId())
                .owner_id(channel.getOwnerId())


                .name(channel.getName())
                .description(channel.getDescription()!=null ? channel.getDescription() : null)
                .nomberofsubscriptions(channel.getSubscriptions()!=null ? channel.getSubscriptions().size() : 0)
                .build();
    }

    public ChannelEntity toEntity(Channel_dto channel_dto){
        return  ChannelEntity.builder()
                .ownerId(channel_dto.getOwner_id())
                .name(channel_dto.getName())
                .description(channel_dto.getDescription()!=null ? channel_dto.getDescription() : null)
                .build();



    }
    public void updateEntityFromDto(ChannelEntity channelEntity,Channel_dto channel_dto){
        channelEntity.setDescription(channel_dto.getDescription()!=null ? channel_dto.getDescription() : channelEntity.getDescription());
        channelEntity.setName(channel_dto.getName()!=null ? channel_dto.getName() : channelEntity.getName());

    }
}

