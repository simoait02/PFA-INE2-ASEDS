package com.aseds.streammicroservice.controller;

import com.aseds.streammicroservice.service.StreamService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("streams")
public class StreamController {

    private final StreamService streamService;

    @Autowired
    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<String> joinStream(@PathVariable String id){
        return ResponseEntity.ok(streamService.joinStream(id));
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<?> leaveStream(@PathVariable String id) {
        try {
            streamService.leaveStream(id);
            return ResponseEntity.ok().body("Successfully left the stream");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
