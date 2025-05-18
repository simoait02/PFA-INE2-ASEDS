package com.aseds.streammicroservice.repository;

import com.aseds.streammicroservice.enums.States;
import com.aseds.streammicroservice.model.StreamEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StreamRepository extends CrudRepository<StreamEntity,String> {
    Optional<StreamEntity> findStreamByStreamKey(String streamKey);

    List<StreamEntity> findAllByChannelId(Integer channelId);
    List<StreamEntity> findAllByState(States state);
}
