package com.aseds.streammicroservice.service;

import com.aseds.streammicroservice.enums.States;
import com.aseds.streammicroservice.mapper.impl.RequestStreamEntityMapper;
import com.aseds.streammicroservice.mapper.impl.StreamEntityDTO;
import com.aseds.streammicroservice.model.StreamEntity;
import com.aseds.streammicroservice.model.dto.StreamDTO;
import com.aseds.streammicroservice.model.dto.StreamRequest;
import com.aseds.streammicroservice.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StreamManagementService {
    private final StreamRepository streamRepository;
    private final RequestStreamEntityMapper requestStreamEntityMapper;
    private final StreamEntityDTO streamEntityDTO;

    @Autowired
    public StreamManagementService(StreamRepository streamRepository, RequestStreamEntityMapper requestStreamEntityMapper, StreamEntityDTO streamEntityDTO) {
        this.streamRepository = streamRepository;
        this.requestStreamEntityMapper = requestStreamEntityMapper;
        this.streamEntityDTO = streamEntityDTO;
    }

    public String createStream(StreamRequest streamRequest){
        String newKey = UUID.randomUUID().toString().replace("-", "");
        StreamEntity streamEntity = requestStreamEntityMapper.mapFrom(streamRequest);
        streamEntity.setStreamKey(newKey);
        streamEntity.setViewCount(0L);
        streamEntity.setStartTime(LocalDateTime.now());
        streamEntity.setState(States.STARTED);
        streamRepository.save(streamEntity);
        return newKey;
    }

    public void endStream(String streamKey){
        StreamEntity streamEntity = streamRepository.findStreamByStreamKey(streamKey).orElseThrow(
                () -> new IllegalArgumentException("stream with the key "+streamKey+" is not found")
        );
        streamEntity.setState(States.ENDED);
        streamEntity.setEndTime(LocalDateTime.now());
        streamRepository.save(streamEntity);
    }
    public List<StreamDTO> getAllStreams(){
        return streamRepository.findAllByState(States.STARTED).stream().map(streamEntityDTO::mapTo).collect(Collectors.toList());
    }

    public List<StreamDTO> getStreamsByChannelId(int channelId) {
        return streamRepository.findAllByChannelId(channelId).stream()
                .filter(streamEntity -> streamEntity.getState() == States.STARTED)
                .map(streamEntityDTO::mapTo)
                .collect(Collectors.toList());
    }

    public StreamDTO getStreamByStreamKey(String streamKey){
        return streamEntityDTO.mapTo(streamRepository.findStreamByStreamKey(streamKey).orElseThrow(
                ()-> new IllegalArgumentException("stream not found")
        ));
    }

}
