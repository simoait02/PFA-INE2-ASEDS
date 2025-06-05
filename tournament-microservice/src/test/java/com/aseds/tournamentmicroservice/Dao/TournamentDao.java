package com.aseds.tournamentmicroservice.Dao;

import com.aseds.tournamentmicroservice.model.Tournament;
import com.aseds.tournamentmicroservice.repository.TournamentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TournamentDao {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    public void findAlltest(){
        Tournament t1=Tournament.builder().title("t1")
                .date(LocalDate.now()).location("l1")
                .description("d1").build();
        Tournament t2=Tournament.builder().title("t2")
                .date(LocalDate.now()).location("l2")
                .description("d2").build();
        Tournament t3=Tournament.builder().title("t3")
                .date(LocalDate.now()).location("l3")
                .description("d3").build();
        entityManager.persist(t1);
        entityManager.persist(t2);
        entityManager.persist(t3);
        entityManager.flush();
        List<Tournament> channels=this.tournamentRepository.findAll();
        assertThat(channels).hasSize(3).contains(t1, t2, t3);
    }
    @Test
    public void findByIdTest(){
        Tournament t=Tournament.builder().title("t")
                .date(LocalDate.now()).location("l")
                .description("d").build();
        entityManager.persist(t);
        Tournament saved=this.tournamentRepository.findById(t.getId()).get();
        assertThat(saved).isEqualTo(t);
    }
    @Test
    public void createChannelTest(){

        Tournament t=Tournament.builder().title("t")
                .date(LocalDate.now()).location("loc")
                .description("des").build();
        this.tournamentRepository.save(t);
        assertThat(t).hasFieldOrPropertyWithValue("title", "t");
        assertThat(t).hasFieldOrPropertyWithValue("description", "des");
        assertThat(t).hasFieldOrPropertyWithValue("location", "loc");
    }
    @Test
    public void deleteChannelTest(){
        Tournament t1=Tournament.builder().title("t1")
                .date(LocalDate.now()).location("l1")
                .description("d1").build();
        Tournament t2=Tournament.builder().title("t2")
                .date(LocalDate.now()).location("l2")
                .description("d2").build();
        Tournament t3=Tournament.builder().title("t3")
                .date(LocalDate.now()).location("l3")
                .description("d3").build();
        entityManager.persist(t1);
        entityManager.persist(t2);
        entityManager.persist(t3);
        this.tournamentRepository.delete(t1);
        List<Tournament> channels=this.tournamentRepository.findAll();
        assertThat(channels).doesNotContain(t1).contains(t3, t2);
    }
}
