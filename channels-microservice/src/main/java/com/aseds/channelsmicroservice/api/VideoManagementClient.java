package com.aseds.channelsmicroservice.api;

import com.aseds.channelsmicroservice.Mappers.VideoResponseMapper;
import com.aseds.channelsmicroservice.models.dto.VideoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class VideoManagementClient {

     private String VIDEO_ENDPOINT= "/channel/";
     private String baseurl;
     private RestTemplate client;

     public VideoManagementClient(RestTemplate restTemplate, @Value("${video.management.api.base-url}") String url){
         this.client=restTemplate;
         this.baseurl=url;
     }

     public List<VideoDTO> getVideosByChannelId(int id){
         try {
             String completurl=this.baseurl + VIDEO_ENDPOINT +id;
             ResponseEntity<VideoResponseMapper> response=this.client
                     .exchange(completurl,
                             HttpMethod.GET,
                             null,
                             VideoResponseMapper.class);
             VideoResponseMapper VideoMapper= response.getBody();
             if(VideoMapper.getEmbedded() != null){
                 return VideoMapper.getEmbedded().getVideoDTOList();
             }
             return List.of();
         }
          catch (RestClientException e) {
             throw new IllegalArgumentException("Failed to retrieve Videos", e);
         }



     }

}
