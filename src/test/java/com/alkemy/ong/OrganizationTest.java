package com.alkemy.ong;

import com.alkemy.ong.data.entities.OrganizationEntity;
import com.alkemy.ong.data.repositories.OrganizationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;


import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class OrganizationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OrganizationRepository organizationRepository;

    private OrganizationEntity organizationEntity = OrganizationEntity.builder()
            .id(1L)
            .name("name")
            .image("img.jpg")
            .phone(123456)
            .address("address")
            .facebook("facebook")
            .instagram("instagram")
            .linkedin("linkedin")
            .build();


    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(authorities  = "USER")
    @DisplayName("Get organization by id, success case")
    public void getByIdTest() throws Exception{
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organizationEntity));

        mockMvc.perform(get("/organization/public/1"))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.image", is("/img.png")))
                .andExpect(jsonPath("$.phone", is(123456)))
                .andExpect(jsonPath("$.address", is("address")))
                .andExpect(jsonPath("$.facebook", is("facebook")))
                .andExpect(jsonPath("$.instagram", is("instagram")))
                .andExpect(jsonPath("$.linkedin", is("linkedin")))

                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getByIDTestError() throws Exception{

        when(organizationRepository.findById(1234L)).thenReturn(Optional.empty());

        mockMvc.perform((MockMvcRequestBuilders.get("/organization/public/1234")).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
    @Test
    @WithMockUser(authorities  = "ADMIN")
    @DisplayName("Update organization by ADMIN, success case")
    public void updateByAdmin() throws Exception{

        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organizationEntity));
        organizationEntity.setPhone(99999);

        when(organizationRepository.save(organizationEntity)).thenReturn(organizationEntity);
        mockMvc.perform(put("/organization/public/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(organizationEntity)))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.image", is("/img.png")))
                .andExpect(jsonPath("$.phone", is(99999)))
                .andExpect(jsonPath("$.address", is("address")))
                .andExpect(jsonPath("$.facebook", is("facebook")))
                .andExpect(jsonPath("$.instagram", is("instagram")))
                .andExpect(jsonPath("$.linkedin", is("linkedin")))

                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(authorities  = "ADMIN")
    @DisplayName("Update organization by ADMIN, Bad Request case")
    public void updateByError() throws Exception{
        organizationEntity.setName(null);
        mockMvc.perform(put("/organization/public/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(organizationEntity)))
                .andExpect(status().isBadRequest());
    }


}
