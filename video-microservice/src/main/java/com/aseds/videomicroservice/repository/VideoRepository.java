package com.aseds.videomicroservice.repository;

import com.aseds.videomicroservice.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends CrudRepository<Video, String>, PagingAndSortingRepository<Video ,String> {
    Page<Video> findByChannelId(int channelId, Pageable pageable);

}
