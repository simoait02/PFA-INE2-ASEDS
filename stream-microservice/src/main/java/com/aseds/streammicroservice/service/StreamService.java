package com.aseds.streammicroservice.service;

import com.aseds.streammicroservice.model.StreamEntity;
import com.aseds.streammicroservice.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StreamService {
    private final String nginxServer;

    private final StreamRepository streamRepository;

    @Autowired
    public StreamService(@Value("${nginx.rtmp}/") String nginxServer, StreamRepository streamRepository) {
        this.nginxServer = nginxServer;
        this.streamRepository = streamRepository;
    }

    public String joinStream(String id){
        StreamEntity streamEntity = streamRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("stream with the id "+id+" is not found")
        );
        streamEntity.setViewCount(streamEntity.getViewCount()+1);
        streamRepository.save(streamEntity);
        return nginxServer + streamEntity.getStreamKey();
    }

    public void leaveStream(String id){
        StreamEntity streamEntity = streamRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("stream with the id "+id+" is not found")
        );
        streamEntity.setViewCount(streamEntity.getViewCount()-1);
        streamRepository.save(streamEntity);
    }
}
