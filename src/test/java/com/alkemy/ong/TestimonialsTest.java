package com.alkemy.ong;

import com.alkemy.ong.data.entities.TestimonialEntity;
import com.alkemy.ong.data.repositories.TestimonialRepository;
import com.alkemy.ong.domain.utils.PageModel;
import com.alkemy.ong.web.controllers.TestimonialController.TestimonialDTO;
import io.swagger.v3.core.util.Json;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DisplayName("Given a testimonial controller")
public class TestimonialsTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TestimonialRepository mockTestimonialRepository;

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("Admins should be able to create testimonial")
    public void createTestimonials() throws Exception {
        String content = "Testimonial Content";
        String name = "Testimonial Name";
        String image = "https://bucket.com/image.jpg";
        TestimonialDTO testimonialDTO = getTestimonialDTO(content, name, image);
        when(mockTestimonialRepository.save(getTestimonialEntity(content, name, image))).thenReturn(getTestimonialEntity(1L, content, name, image));
        ResultActions resultActions = performHttpAction(post("/testimonials"), testimonialDTO);
        checkResponseTestimonials(status().isCreated(), resultActions, 1, content, name, image);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("Try to create a invalid testimonial wield return error")
    public void createInvalidTestimonial() throws Exception {
        TestimonialDTO testimonialDTO = getTestimonialDTO("Content", "", "");
        performHttpAction(post("/testimonials"), testimonialDTO).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("Get all testimonials. Ten by page")
    public void getTestimonial() throws Exception {
        List<TestimonialEntity> testimonialEntities = Arrays.asList(
                getTestimonialEntity(1L, "Contenido 1", "content 1", "imagen1"),
                getTestimonialEntity(2L, "Contenido 2", "content 2", "imagen2"),
                getTestimonialEntity(3L, "Contenido 3", "content 3", "imagen3")
        );

        PageModel<TestimonialEntity> pageModel = PageModel.<TestimonialEntity>builder().content(testimonialEntities).build();
        when(mockTestimonialRepository.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(pageModel.getContent()));
        mockMvc.perform(MockMvcRequestBuilders.get("/testimonials").param("page", "0").contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(pageModel)))
                .andExpect(jsonPath("$.content.[0].id", is(1)))
                .andExpect(jsonPath("$.content.[1].id", is(2)))
                .andExpect(jsonPath("$.content.[2].id", is(3)))
                .andExpect(jsonPath("$.content.[0].name", is("content 1")))
                .andExpect(jsonPath("$.content.[1].name", is("content 2")))
                .andExpect(jsonPath("$.content.[2].name", is("content 3")))
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("Error at get all testimonial. Index incorrect")
    public void getTestimonialError() throws Exception {
        List<TestimonialEntity> testimonialEntities = Arrays.asList();
        PageModel<TestimonialEntity> pageModel = PageModel.<TestimonialEntity>builder().content(testimonialEntities).build();
        when(mockTestimonialRepository.findAll(PageRequest.of(1, 10))).thenReturn(new PageImpl<>(pageModel.getContent()));
        mockMvc.perform(MockMvcRequestBuilders.get("/testimonials").param("page", "1").contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(pageModel)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("Update testimonial")
    public void updateTestimonial() throws Exception {
        TestimonialEntity testimonialEntity = getTestimonialEntity(1L, "Testimonial 1", "Content 1", "No imagen");
        when(mockTestimonialRepository.findById(testimonialEntity.getId())).thenReturn(Optional.of(testimonialEntity));
        when(mockTestimonialRepository.save(testimonialEntity)).thenReturn(testimonialEntity);

        ResultActions resultActions = performHttpAction(put("/testimonials/1"), getTestimonialDTO(1L, "Testimonial 1", "Content 1", "No imagen"));
        checkResponseTestimonials(status().isOk(), resultActions, 1, "Testimonial 1", "Content 1", "No imagen");
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("Error at update testimonial. Testimonial's Id, not found")
    public void updateTestimonialError() throws Exception {
        TestimonialEntity testimonialEntity = getTestimonialEntity("Content 2", "No imagen");
        when(mockTestimonialRepository.findById(testimonialEntity.getId())).thenReturn(Optional.of(testimonialEntity));
        when(mockTestimonialRepository.save(testimonialEntity)).thenReturn(testimonialEntity);
        performHttpAction(put("/testimonials/2"), getTestimonialDTO("Testimonial 1", "Content 2", "No imagen")).andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("Delete testimonial by ID")
    public void deleteTestimonial() throws Exception {
        TestimonialEntity testimonialEntity = getTestimonialEntity(1L, "Testimonial 1", "Content 1", "No imagen");
        testimonialEntity.setDeleted(true);

        when(mockTestimonialRepository.findById(testimonialEntity.getId())).thenReturn(Optional.of(testimonialEntity));
        when(mockTestimonialRepository.save(testimonialEntity)).thenReturn(testimonialEntity);

        mockMvc.perform((MockMvcRequestBuilders.delete("/testimonials/1")))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("Error at delete testimonial. Testimonial's Id, not found")
    public void deleteTestimonialError() throws Exception {
        when(mockTestimonialRepository.findById(100L)).thenReturn(Optional.empty());
        mockMvc.perform((MockMvcRequestBuilders.delete("/testimonials/100")).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private ResultActions performHttpAction(MockHttpServletRequestBuilder httpAction, TestimonialDTO testimonialDTO) throws Exception {
        return mockMvc.perform(httpAction.contentType(MediaType.APPLICATION_JSON).content(Json.mapper().writeValueAsString(testimonialDTO)));
    }

    private void checkResponseTestimonials(ResultMatcher expectedStatus, ResultActions resultActions, Integer id, String content, String name, String image) throws Exception {
        resultActions
                .andExpect(expectedStatus)
                .andExpect(jsonPath("$.id", Is.is(id)))
                .andExpect(jsonPath("$.content", Is.is(content)))
                .andExpect(jsonPath("$.name", Is.is(name)))
                .andExpect(jsonPath("$.image", Is.is(image)));
    }

    private TestimonialDTO getTestimonialDTO(String content, String name, String image) {
        return TestimonialDTO.builder()
                .content(content)
                .name(name)
                .image(image)
                .build();
    }

    private TestimonialDTO getTestimonialDTO(Long id, String content, String name, String image) {
        return TestimonialDTO.builder()
                .id(id)
                .content(content)
                .name(name)
                .image(image)
                .build();
    }

    private TestimonialEntity getTestimonialEntity(Long id, String content, String name, String image) {
        return TestimonialEntity.builder()
                .id(id)
                .content(content)
                .name(name)
                .image(image)
                .build();
    }

    private TestimonialEntity getTestimonialEntity(String content, String name, String image) {
        return TestimonialEntity.builder()
                .content(content)
                .name(name)
                .image(image)
                .build();
    }

    private TestimonialEntity getTestimonialEntity(String name, String image) {
        return TestimonialEntity.builder()
                .name(name)
                .image(image)
                .build();
    }


}
