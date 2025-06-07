package com.aseds.blogsmicroservice.blogs_microservice.Controller;

import com.aseds.blogsmicroservice.blogs_microservice.controller.BlogController;
import com.aseds.blogsmicroservice.blogs_microservice.mappers.BlogMapper;
import com.aseds.blogsmicroservice.blogs_microservice.models.BlogEntity;
import com.aseds.blogsmicroservice.blogs_microservice.models.dto.BlogDto;
import com.aseds.blogsmicroservice.blogs_microservice.repositories.BlogRepository;
import com.aseds.blogsmicroservice.blogs_microservice.services.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BlogController.class)
public class BlogControllerUnit {
    @Autowired
    public MockMvc mockMvc;
    @MockitoBean
    public BlogService blogService;
    ObjectMapper mapper=new ObjectMapper();
    String resultContent=null;
    @Test
    public void getAllChannels() throws Exception{
        BlogDto b1= BlogDto.builder()
                .title("t1").content("c1").owner_id(1)
                .category("ca1")
                .build();
        BlogDto b2= BlogDto.builder()
                .title("t2").content("c2").owner_id(2)
                .category("ca2")
                .build();
        List<BlogDto> blogDtos = Arrays.asList(
                b1,b2
        );
        resultContent = mapper.writeValueAsString(blogDtos);
        System.out.println(resultContent);
        when(blogService.getAllBlogs()).thenReturn(blogDtos);

        mockMvc.perform(get("/blogs/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(resultContent)));
    }
    @Test
    public void getChannelByOwnerId() throws Exception{
        BlogDto b1= BlogDto.builder()
                .title("t1").content("c1").owner_id(1)
                .category("ca1")
                .build();
        resultContent=mapper.writeValueAsString(b1);
        when(blogService.getBlogById(1)).thenReturn(b1);
        mockMvc.perform(get("/blogs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(resultContent)));
    }
    @Test
    public void CreateChannel() throws Exception{
        BlogDto b1= BlogDto.builder()
                .title("t1").content("c1").owner_id(1)
                .category("ca1")
                .build();
        resultContent=mapper.writeValueAsString(b1);
        when(blogService.createBlog(Mockito.any(BlogDto.class))).thenReturn(b1);
        this.mockMvc.perform(post("/blogs/")
                .content(resultContent)
                .contentType(MediaType.APPLICATION_JSON));

    }
    @Test
    public void updateChannel() throws Exception{
        BlogDto b1= BlogDto.builder()
                .title("t1").content("c1").owner_id(1)
                .category("ca1")
                .build();
        resultContent=mapper.writeValueAsString(b1);
        b1.setId(1);
        when(blogService.updateBlog(Mockito.any(BlogDto.class),Mockito.any(Integer.class))).thenReturn(b1);
        this.mockMvc.perform(patch("/blogs/1")
                        .content(resultContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
