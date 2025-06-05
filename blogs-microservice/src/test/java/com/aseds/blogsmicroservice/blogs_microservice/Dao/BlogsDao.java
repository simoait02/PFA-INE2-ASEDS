package com.aseds.blogsmicroservice.blogs_microservice.Dao;

import com.aseds.blogsmicroservice.blogs_microservice.models.BlogEntity;
import com.aseds.blogsmicroservice.blogs_microservice.repositories.BlogRepository;
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
public class BlogsDao {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BlogRepository blogRepository;

    @Test
    public void findAlltest(){
        BlogEntity blog1=BlogEntity.builder().title("b1")
                .content("c1").ownerid(1).category("ca1").build();
        BlogEntity blog2=BlogEntity.builder().title("b2")
                .content("c2").ownerid(1).category("ca2").build();
        BlogEntity blog3=BlogEntity.builder().title("b3")
                .content("c3").ownerid(2).category("ca3").build();
        entityManager.persist(blog1);
        entityManager.persist(blog2);
        entityManager.persist(blog3);
        entityManager.flush();
        List<BlogEntity> channels=this.blogRepository.findAll();
        assertThat(channels).hasSize(3).contains(blog1, blog2, blog3);
    }
    @Test
    public void findByIdTest(){
        BlogEntity blog=BlogEntity.builder().title("b4")
                .content("c4").ownerid(3).category("ca4").build();
        entityManager.persist(blog);
        BlogEntity saved=this.blogRepository.findById(blog.getId()).get();
        assertThat(saved).isEqualTo(blog);
    }
    @Test
    public void createChannelTest(){

        BlogEntity blog=BlogEntity.builder().title("b5")
                .content("c5").ownerid(4).category("ca5").build();
        this.blogRepository.save(blog);
        assertThat(blog).hasFieldOrPropertyWithValue("title", "b5");
        assertThat(blog).hasFieldOrPropertyWithValue("content", "c5");
        assertThat(blog).hasFieldOrPropertyWithValue("category", "ca5");
        assertThat(blog).hasFieldOrPropertyWithValue("ownerid", 4);
    }
    @Test
    public void deleteChannelTest(){
        BlogEntity blog1=BlogEntity.builder().title("b1")
                .content("c1").ownerid(1).category("ca1").build();
        BlogEntity blog2=BlogEntity.builder().title("b2")
                .content("c2").ownerid(1).category("ca2").build();
        BlogEntity blog3=BlogEntity.builder().title("b3")
                .content("c3").ownerid(2).category("ca3").build();
        entityManager.persist(blog1);
        entityManager.persist(blog2);
        entityManager.persist(blog3);
        this.blogRepository.delete(blog1);
        List<BlogEntity> blogs=this.blogRepository.findAll();
        assertThat(blogs).doesNotContain(blog1).contains(blog2, blog3);
    }
}
