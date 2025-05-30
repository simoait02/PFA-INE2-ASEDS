package com.aseds.videomicroservice.service;

import com.aseds.videomicroservice.api.ChannelManagementClient;
import com.aseds.videomicroservice.exceptions.VideoNotFoundException;
import com.aseds.videomicroservice.mapper.impl.VideoMapper;
import com.aseds.videomicroservice.mapper.impl.VideoMapperDTO;
import com.aseds.videomicroservice.model.Comment;
import com.aseds.videomicroservice.model.Video;
import com.aseds.videomicroservice.model.dto.VideoDTO;
import com.aseds.videomicroservice.model.dto.VideoRequest;
import com.aseds.videomicroservice.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private static final String VIDEO_NOT_FOUND_MESSAGE = "Video not found for ID: ";

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final VideoMapperDTO videoMapperDTO;
    private final ChannelManagementClient channelManagementClient;

    public VideoDTO createVideo(VideoRequest videoRequest) {
        validateVideoRequest(videoRequest);
        if(!channelManagementClient.isChannelExist(videoRequest.getChannelId())){
            throw new IllegalArgumentException("can't upload video channel not exist");
        }
        videoRequest.setUploadDate(LocalDateTime.now());
        Video newVideo = videoMapper.mapTo(videoRequest);
        newVideo.setComments(new ArrayList<>());
        newVideo.setVideoUrl(videoRequest.getVideoUrl());
        Video savedVideo = videoRepository.save(newVideo);
        return videoMapperDTO.mapTo(savedVideo);
    }

    public VideoDTO getVideoById(String id) {
        validateId(id);
        Video video = findVideoOrThrow(id);
        return videoMapperDTO.mapTo(video);
    }

    public PagedModel<VideoDTO> getAllChannelVideos(int channelId, Pageable pageable) {
        validateChannelId(channelId);

        try {
            Page<Video> videosPage = videoRepository.findByChannelId(channelId, pageable);
            List<VideoDTO> videoDTOs = convertToVideoDTOList(videosPage);
            return createPagedModel(videosPage, videoDTOs, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving videos for channel: " + channelId, e);
        }
    }

    public void deleteVideoById(String id) {
        validateId(id);
        videoRepository.deleteById(id);
    }

    public void addComment(String videoId, Comment comment) {
        validateId(videoId);
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }

        Video video = findVideoOrThrow(videoId);
        prepareComment(comment, videoId);
        video.getComments().add(comment);
        videoRepository.save(video);
    }

    private void validateVideoRequest(VideoRequest videoRequest) {
        if (videoRequest == null) {
            throw new IllegalArgumentException("Video request cannot be null");
        }
    }

    private void validateId(String id) {
        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
    }

    private void validateChannelId(int channelId) {
        if (channelId <= 0) {
            throw new IllegalArgumentException("Channel ID must be positive");
        }
    }

    private Video findVideoOrThrow(String id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException(VIDEO_NOT_FOUND_MESSAGE + id));
    }

    private void prepareComment(Comment comment, String videoId) {
        comment.setVideoId(videoId);
        comment.setUploadDate(LocalDateTime.now());
    }

    private List<VideoDTO> convertToVideoDTOList(Page<Video> videosPage) {
        return videosPage.getContent().stream()
                .map(videoMapperDTO::mapTo)
                .collect(Collectors.toList());
    }

    private PagedModel<VideoDTO> createPagedModel(Page<Video> videosPage, List<VideoDTO> videoDTOs, Pageable pageable) {
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                pageable.getPageSize(),
                videosPage.getNumber(),
                videosPage.getTotalElements(),
                videosPage.getTotalPages());
        return PagedModel.of(videoDTOs, metadata);
    }
}