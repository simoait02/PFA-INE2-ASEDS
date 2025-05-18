package com.aseds.streammicroservice.controller;

import com.aseds.streammicroservice.model.dto.StreamDTO;
import com.aseds.streammicroservice.model.dto.StreamRequest;
import com.aseds.streammicroservice.service.StreamManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("streams-management")
public class StreamManagementController {
    private final StreamManagementService streamManagementService;

    @Autowired
    public StreamManagementController(StreamManagementService streamManagementService) {
        this.streamManagementService = streamManagementService;
    }

    @PostMapping
    public ResponseEntity<String> createStream(@RequestBody StreamRequest streamRequest) {
        try {
            String streamKey = streamManagementService.createStream(streamRequest);
            return ResponseEntity.ok(streamKey);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create stream: " + e.getMessage());
        }
    }

    @PostMapping("/{streamKey}/end")
    public ResponseEntity<Void> endStream(@PathVariable String streamKey) {
        try {
            streamManagementService.endStream(streamKey);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<StreamDTO>> getAllStreams(){
        try {

            return ResponseEntity.ok(streamManagementService.getAllStreams());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/id/{channelId}")
    public ResponseEntity<List<StreamDTO>> getStreamByChannelId(@PathVariable Integer channelId){
        try {
            return ResponseEntity.ok(streamManagementService.getStreamsByChannelId(channelId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/key/{streamKey}")
    public ResponseEntity<StreamDTO> getStreamByStreamKey(@PathVariable String streamKey){
        try {
            return ResponseEntity.ok(streamManagementService.getStreamByStreamKey(streamKey));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
