package com.alkemy.ong;

import com.alkemy.ong.data.entities.CategoryEntity;
import com.alkemy.ong.data.repositories.CategoryRepository;
import com.alkemy.ong.domain.exceptions.BadRequestException;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.utils.PageModel;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CategoryTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = "USER")
    public void getAllCategoryBasicByPageTest() throws Exception{
        List<CategoryEntity> categoryEntities = Arrays.asList(
                createCategory(1L,"Health", "Health is very important for the world", "health.jpg"),
                createCategory(2L,"Greenpeace", "Nature protection", "greenpeace.jpg"),
                createCategory(3L,"UNICEF", "Best solidarity", "unicef.jpg")
        );

        PageModel<CategoryEntity> pageModel =
                PageModel.<CategoryEntity>builder()
                        .content(categoryEntities)
                        .build();

        when(categoryRepository.findAll(PageRequest.of(0,10))).thenReturn(new PageImpl<>(pageModel.getContent()));

        mockMvc.perform(MockMvcRequestBuilders.get("/categories").param("page","0").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pageModel)))
                .andExpect(jsonPath("$.content.[0].id", is(1)))
                .andExpect(jsonPath("$.content.[1].id", is(2)))
                .andExpect(jsonPath("$.content.[2].id", is(3)))
                .andExpect(jsonPath("$.content.[0].name", is("Health")))
                .andExpect(jsonPath("$.content.[1].name", is("Greenpeace")))
                .andExpect(jsonPath("$.content.[2].name", is("UNICEF")))
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void getCategoryByIDTest() throws Exception{
        var categoryEntity = createCategory(1L,"Health", "Health is very important for the world", "health.jpg");

        when(categoryRepository.findById(categoryEntity.getId())).thenReturn(Optional.of(categoryEntity));

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntity)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Health")))
                .andExpect(jsonPath("$.description", is("Health is very important for the world")))
                .andExpect(jsonPath("$.image", is("health.jpg")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getCategoryByIDTestError() throws Exception{

        when(categoryRepository.findById(1234L)).thenReturn(Optional.empty());

        mockMvc.perform((MockMvcRequestBuilders.get("/categories/1234")).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveTest() throws Exception{
        var categoryEntity = createCategory(null,"Health", "Health is very important for the world", "health.jpg");
        var categoryEntityResponse = createCategory(1L,"Health", "Health is very important for the world", "health.jpg");

        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntityResponse);

        mockMvc.perform((MockMvcRequestBuilders.post("/categories")).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntityResponse)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Health")))
                .andExpect(jsonPath("$.description", is("Health is very important for the world")))
                .andExpect(jsonPath("$.image", is("health.jpg")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveTestError() throws Exception{
        var categoryEntity = createCategory(null, null, "Health is very important for the world", "health.jpg");

        mockMvc.perform((MockMvcRequestBuilders.post("/categories")).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntity)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteTest() throws Exception{
        CategoryEntity categoryEntity = createCategory(1L,"Health", "Health is very important for the world", "health.jpg");
        categoryEntity.setIsDeleted(true);

        when(categoryRepository.findById(categoryEntity.getId())).thenReturn(Optional.of(categoryEntity));
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);

        mockMvc.perform((MockMvcRequestBuilders.delete("/categories/1")))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteTestError() throws Exception{

        when(categoryRepository.findById(1234L)).thenReturn(Optional.empty());

        mockMvc.perform((MockMvcRequestBuilders.delete("/categories/1234")).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void updateTest() throws Exception{
        CategoryEntity categoryEntity = createCategory(1L,"Health", "Health is very important for the world", "health.jpg");

        when(categoryRepository.findById(categoryEntity.getId())).thenReturn(Optional.of(categoryEntity));
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);

        mockMvc.perform((MockMvcRequestBuilders.put("/categories/1")).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntity)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Health")))
                .andExpect(jsonPath("$.description", is("Health is very important for the world")))
                .andExpect(jsonPath("$.image", is("health.jpg")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void updateTestError() throws Exception{
        CategoryEntity categoryEntity = createCategory(1L,null, "Health is very important for the world", "health.jpg");

        when(categoryRepository.findById(categoryEntity.getId())).thenReturn(Optional.of(categoryEntity));

        mockMvc.perform(MockMvcRequestBuilders.put("/categories/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntity)))
                .andExpect(status().isBadRequest());

    }

    private CategoryEntity createCategory(Long id, String name,String description, String image){
        CategoryEntity category = new CategoryEntity();
        category.setId(id);
        category.setName(name);
        category.setDescription(description);
        category.setImage(image);
        category.setIsDeleted(false);
        return category;
    }
}
