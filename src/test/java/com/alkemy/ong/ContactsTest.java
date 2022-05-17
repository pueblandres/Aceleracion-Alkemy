package com.alkemy.ong;

import com.alkemy.ong.data.entities.ContactEntity;
import com.alkemy.ong.data.repositories.ContactRepository;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ContactsTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContactRepository contactRepo;

    private ContactEntity contactEntity = ContactEntity.builder()
            .id(1L)
            .name("contact")
            .email("contact@alkemy.com")
            .phone("1144054522")
            .message("tocayo")
            .build();

    @Test
    @WithMockUser(authorities  = "ADMIN")
    @DisplayName("Save contact by ADMIN, success case")
    public void saveByAdminSuccess() throws Exception{
        var entity = createEntity();
        when(contactRepo.save(entity)).thenReturn(contactEntity);

        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(entity)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("contact")))
                .andExpect(jsonPath("$.email", is("contact@alkemy.com")))
                .andExpect(jsonPath("$.phone", is("1144054522")))
                .andExpect(jsonPath("$.message", is("tocayo")))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities  = "ADMIN")
    @DisplayName("Save contact by ADMIN, Bad Request case")
    public void saveByAdminError() throws Exception{
        contactEntity.setName(null);
        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(contactEntity)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities  = "USER")
    @DisplayName("Save contact by USER, Forbidden case")
    public void saveByUserError() throws Exception{
        contactEntity.setName(null);
        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.mapper().writeValueAsString(contactEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    @DisplayName("Get list of contacts")
    public void getContactsSuccess() throws Exception {
        when(contactRepo.findAll()).thenReturn(List.of(
                ContactEntity.builder().id(1L).name("Contact 1").build(),
                ContactEntity.builder().id(2L).name("Contact 2").build()
        ));
        mockMvc.perform(get("/contacts")).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Contact 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Contact 2")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    private ContactEntity createEntity(){
        return ContactEntity.builder()
                .id(null)
                .name("contact")
                .email("contact@alkemy.com")
                .phone("1144054522")
                .message("tocayo!")
                .build();
    }
}