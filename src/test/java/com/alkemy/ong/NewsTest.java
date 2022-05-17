package com.alkemy.ong;


import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.repositories.NewsRepository;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class NewsTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NewsRepository newsRepo;
    private NewsEntity newsEntity = NewsEntity.builder()
                .id(1L)
                .name("news")
                .content("content")
                .image("/img.png")
                .categoryId(1L)
                .build();

    @Test
    @WithMockUser(authorities  = "ADMIN")
    @DisplayName("Save news by ADMIN, success case")
    public void saveSuccess() throws Exception{
        var entity = createEntity();
        when(newsRepo.save(entity)).thenReturn(newsEntity);

        mockMvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.mapper().writeValueAsString(entity)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("news")))
                .andExpect(jsonPath("$.content", is("content")))
                .andExpect(jsonPath("$.image", is("/img.png")))
                .andExpect(jsonPath("$.categoryId", is(1)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities  = "USER")
    @DisplayName("Save news by USER, Forbidden case")
    public void saveByUserError() throws Exception{
        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(newsEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities  = "ADMIN")
    @DisplayName("Save news by ADMIN, Bad Request case")
    public void saveByAdminError() throws Exception{
        newsEntity.setName(null);
        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(newsEntity)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities  = "USER")
    @DisplayName("Get news by id, success case")
    public void getById() throws Exception{
        when(newsRepo.findById(1L)).thenReturn(Optional.of(newsEntity));

        mockMvc.perform(get("/news/1"))
                .andExpect(jsonPath("$.name", is("news")))
                .andExpect(jsonPath("$.content", is("content")))
                .andExpect(jsonPath("$.image", is("/img.png")))
                .andExpect(jsonPath("$.categoryId", is(1)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities  = "ADMIN")
    @DisplayName("Delete news by ADMIN, success case")
    public void deleteSuccess() throws Exception{
        when(newsRepo.findById(1L)).thenReturn(Optional.of(newsEntity));

        mockMvc.perform(delete("/news/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities  = "ADMIN")
    @DisplayName("Delete news by ADMIN, Not Found case")
    public void deleteByAdminError() throws Exception{
        when(newsRepo.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/news/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities  = "USER")
    @DisplayName("Delete news by USER, Forbidden case")
    public void deleteByUserError() throws Exception{
        mockMvc.perform(delete("/news/1"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(authorities  = "ADMIN")
    @DisplayName("Update news by ADMIN, success case")
    public void updateByAdmin() throws Exception{
        when(newsRepo.findById(1L)).thenReturn(Optional.of(newsEntity));
        newsEntity.setContent("¡Updated content!");

        when(newsRepo.save(newsEntity)).thenReturn(newsEntity);
        mockMvc.perform(put("/news/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(newsEntity)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("news")))
                .andExpect(jsonPath("$.content", is("¡Updated content!")))
                .andExpect(jsonPath("$.image", is("/img.png")))
                .andExpect(jsonPath("$.categoryId", is(1)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities  = "USER")
    @DisplayName("Update news by USER, Forbidden case")
    public void updateByUser() throws Exception{
        newsEntity.setContent("¡Updated content!");
        mockMvc.perform(put("/news/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(newsEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities  = "ADMIN")
    @DisplayName("Update news by ADMIN, Bad Request case")
    public void updateByAdminError() throws Exception{
        newsEntity.setContent(null);
        mockMvc.perform(put("/news/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(newsEntity)))
                .andExpect(status().isBadRequest());
    }

    private NewsEntity createEntity() {
        return NewsEntity.builder()
                .id(null)
                .name("news")
                .content("content")
                .image("/img.png")
                .categoryId(1L)
                .build();
    }
}
