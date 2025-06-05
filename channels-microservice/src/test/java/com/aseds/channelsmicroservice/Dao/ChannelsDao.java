package com.aseds.channelsmicroservice.Dao;

import com.aseds.channelsmicroservice.models.ChannelEntity;
import com.aseds.channelsmicroservice.repositories.Channel_repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ChannelsDao {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private Channel_repository channel_repository;

    @Test
    public void findAlltest(){
        ChannelEntity channel1=ChannelEntity.builder().name("ch1")
                .description("des1").ownerId(1).build();
        ChannelEntity channel2=ChannelEntity.builder().name("ch2")
                .description("des2").ownerId(2).build();
        ChannelEntity channel3=ChannelEntity.builder().name("ch3")
                .description("des3").ownerId(3).build();
        entityManager.persist(channel1);
        entityManager.persist(channel2);
        entityManager.persist(channel3);
        entityManager.flush();
        List<ChannelEntity> channels=this.channel_repository.findAll();
        assertThat(channels).hasSize(3).contains(channel1, channel2, channel3);
    }
    @Test
    public void findByIdTest(){
         ChannelEntity channel=ChannelEntity.builder().name("ch")
                 .description("des").ownerId(1).build();
         entityManager.persist(channel);
         ChannelEntity saved=this.channel_repository.findById(channel.getId()).get();
         assertThat(saved).isEqualTo(channel);
    }
    @Test
    public void createChannelTest(){

        ChannelEntity c  = ChannelEntity.builder().name("channel")
                .description("description").ownerId(4).build();
        this.channel_repository.save(c);
        assertThat(c).hasFieldOrPropertyWithValue("name", "channel");
        assertThat(c).hasFieldOrPropertyWithValue("description", "description");
        assertThat(c).hasFieldOrPropertyWithValue("ownerId", 4);
    }
    @Test
    public void deleteChannelTest(){
        ChannelEntity channel1=ChannelEntity.builder().name("ch1")
                .description("des1").ownerId(1).build();
        ChannelEntity channel2=ChannelEntity.builder().name("ch2")
                .description("des2").ownerId(2).build();
        ChannelEntity channel3=ChannelEntity.builder().name("ch3")
                .description("des3").ownerId(3).build();
        entityManager.persist(channel1);
        entityManager.persist(channel2);
        entityManager.persist(channel3);
        this.channel_repository.delete(channel1);
        List<ChannelEntity> channels=this.channel_repository.findAll();
        assertThat(channels).doesNotContain(channel1).contains(channel2, channel3);
    }
}
