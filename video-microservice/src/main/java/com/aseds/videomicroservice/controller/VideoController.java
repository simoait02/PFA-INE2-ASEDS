package com.aseds.videomicroservice.controller;

import com.aseds.videomicroservice.model.Comment;
import com.aseds.videomicroservice.model.dto.VideoDTO;
import com.aseds.videomicroservice.model.dto.VideoRequest;
import com.aseds.videomicroservice.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
public class VideoController {
    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    public ResponseEntity<VideoDTO> addVideo(@RequestBody VideoRequest videoRequest) {
        VideoDTO createdVideo = videoService.createVideo(videoRequest);
        return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoDTO> getVideoById(@PathVariable String id) {
        VideoDTO video = videoService.getVideoById(id);
        return ResponseEntity.ok(video);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<PagedModel<VideoDTO>> getAllUserVideos(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedModel<VideoDTO> videos = videoService.getAllUserVideos(id, PageRequest.of(page, size));
        return ResponseEntity.ok(videos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoById(@PathVariable String id) {
        videoService.deleteVideoById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> addComment(@PathVariable String id, @RequestBody Comment comment) {
        videoService.addComment(id, comment);
        return ResponseEntity.ok("Comment added successfully");
    }
}