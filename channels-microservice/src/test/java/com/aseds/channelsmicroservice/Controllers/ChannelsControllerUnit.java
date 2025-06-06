package com.aseds.channelsmicroservice.Controllers;

import com.aseds.channelsmicroservice.Controller.ControllerChannel;
import com.aseds.channelsmicroservice.Mappers.Mapper;
import com.aseds.channelsmicroservice.Services.ServiceChannelManagement;
import com.aseds.channelsmicroservice.models.ChannelEntity;
import com.aseds.channelsmicroservice.models.dto.Channel_dto;
import com.aseds.channelsmicroservice.repositories.Channel_repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ControllerChannel.class)
public class ChannelsControllerUnit {
    @Autowired
    public MockMvc mockMvc;
    @MockitoBean
    public ServiceChannelManagement channelManagement;
    ObjectMapper mapper=new ObjectMapper();
    String resultContent=null;
    @Test
    public void getAllChannels() throws Exception{
        Channel_dto c1= Channel_dto.builder()
                .name("c1").description("d1").owner_id(1)
                .build();
        Channel_dto c2= Channel_dto.builder()
                .name("c2").description("d2").owner_id(2)
                .build();
        List<Channel_dto> channelDtos = Arrays.asList(
                c1,c2
        );
        resultContent = mapper.writeValueAsString(channelDtos);
        System.out.println(resultContent);
        when(channelManagement.getChannels()).thenReturn(channelDtos);

        mockMvc.perform(get("/channels/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(resultContent)));
    }
    @Test
    public void getChannelByOwnerId() throws Exception{
        Channel_dto c1= Channel_dto.builder()
                .name("c1").description("d1").owner_id(1)
                .build();
        resultContent=mapper.writeValueAsString(c1);
        when(channelManagement.getChannelByOwnerId(1)).thenReturn(c1);
        mockMvc.perform(get("/channels/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(resultContent)));
    }
    @Test
    public void CreateChannel() throws Exception{
        Channel_dto c1= Channel_dto.builder()
                .name("c1").description("d1").owner_id(1)
                .build();
        resultContent=mapper.writeValueAsString(c1);
        when(channelManagement.createChannel(Mockito.any(Channel_dto.class))).thenReturn(c1);
        this.mockMvc.perform(post("/channels/")
                        .content(resultContent)
                        .contentType(MediaType.APPLICATION_JSON));

    }
    @Test
    public void updateChannel() throws Exception{
        Channel_dto c1= Channel_dto.builder()
                .name("c1").description("d1").owner_id(1)
                .build();
        resultContent=mapper.writeValueAsString(c1);
        c1.setId(1);
        when(channelManagement.updateChannel(Mockito.any(Integer.class),Mockito.any(Channel_dto.class))).thenReturn(c1);
        this.mockMvc.perform(patch("/channels/1")
                        .content(resultContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
