package com.aseds.channelsmicroservice.Controller;


import com.aseds.channelsmicroservice.Services.ServiceChannelSubscriptions;
import com.aseds.channelsmicroservice.models.dto.Channel_dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/channels/subscription")
public class ControllerSubscriptions {
    @Autowired
    private ServiceChannelSubscriptions serviceChannelSubscriptions;

    @PostMapping("/{idchannel}/users/{iduser}")
    public ResponseEntity<String> subscrib(@PathVariable("idchannel") int channel_id,@PathVariable("iduser") int user_id){
        this.serviceChannelSubscriptions.subscribed(channel_id,user_id);
        return ResponseEntity.status(201).body("YOU HAVE BEEN SUBSCRIBED");

    }
    @DeleteMapping("/{idchannel}/users/{iduser}")
    public  ResponseEntity<String> unsubscrib(@PathVariable("idchannel") int channel_id,@PathVariable("iduser") int user_id){
        this.serviceChannelSubscriptions.unsubscribed(channel_id,user_id);
        return ResponseEntity.status(201).body("YOU HAVE BEEN UNSUBSCRIBED");
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Channel_dto>> getSubscribedChannels(@PathVariable int id){
        return ResponseEntity.ok(this.serviceChannelSubscriptions.getSubscribedChannels(id));
    }

}
