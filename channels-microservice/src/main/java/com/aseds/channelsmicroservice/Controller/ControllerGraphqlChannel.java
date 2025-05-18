package com.aseds.channelsmicroservice.Controller;

import com.aseds.channelsmicroservice.Services.ServiceChannelManagement;
import com.aseds.channelsmicroservice.api.VideoManagementClient;
import com.aseds.channelsmicroservice.models.dto.Channel_dto;
import com.aseds.channelsmicroservice.models.dto.VideoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Controller
public class ControllerGraphqlChannel {

    @Autowired
    private VideoManagementClient videoManagementClient;
    @Autowired
    private ServiceChannelManagement serviceChannel;

    @QueryMapping
    public Optional<Channel_dto> channelById(@Argument Integer id){
        return Optional.of(this.serviceChannel.getChannelById(id));

    }
    @SchemaMapping(typeName = "Channel",field = "videos")
    public List<VideoDTO> videos(Channel_dto channelDto){
        return this.videoManagementClient.getVideosByChannelId(channelDto.getId());
    }
    @QueryMapping
    public Optional<Channel_dto> channelByOwner(@Argument Integer id){
        return Optional.of(this.serviceChannel.getChannelByOwnerId(id));
    }


}
