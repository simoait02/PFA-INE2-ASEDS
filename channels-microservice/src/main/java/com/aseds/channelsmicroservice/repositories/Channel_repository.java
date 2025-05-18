package com.aseds.channelsmicroservice.repositories;

import com.aseds.channelsmicroservice.models.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Channel_repository extends JpaRepository<ChannelEntity,Integer> {

    Optional<ChannelEntity> findByOwnerId(int id);
    // for getting  all the channels that the user has been subscribed
    @Query("SELECT c FROM ChannelEntity c WHERE :userId IN elements(c.subscriptions)")
    List<ChannelEntity> findAllBySubscriberId(@Param("userId") int userId);
    // to find all the channels that contains the target name
    List<ChannelEntity> findByNameContainingIgnoreCase(String name);


}
