package com.aseds.channelsmicroservice.Controller;


import com.aseds.channelsmicroservice.Services.service_channel_management;
import com.aseds.channelsmicroservice.models.dto.Channel_dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/channels")
public class ControllerChannel {
    @Autowired
    private service_channel_management service;

    @GetMapping("/")
    public ResponseEntity<List<Channel_dto>> getAllChannels(){
        return ResponseEntity.ok(this.service.getChannels());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Channel_dto> getChannelById(@PathVariable int id){
        return ResponseEntity.ok(this.service.getChannelById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Channel_dto> createChannel(@RequestBody Channel_dto channel_dto){
        return ResponseEntity.status(201).body(this.service.createChannel(channel_dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Channel_dto> updateChannel(@PathVariable int id,@RequestBody Channel_dto channel_dto){
        return ResponseEntity.ok(this.service.updateChannel(id, channel_dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChannel(@PathVariable int id){
        this.service.deleteChannel(id);
        return ResponseEntity.status(201).body("USER DELETED");
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<Channel_dto> getChannelByOwen(@PathVariable int id){
        return ResponseEntity.ok(this.service.getChannelByOwnerId(id));
    }

}
