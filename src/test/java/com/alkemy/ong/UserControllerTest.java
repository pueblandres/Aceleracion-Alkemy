package com.alkemy.ong;

import com.alkemy.ong.data.entities.RoleEntity;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.UserRepository;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DisplayName("Given a user controller")
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository mockUserRepository;

    @Test
    @WithMockUser(authorities = {"ADMIN", "2"}, username = "admin@mail.com", password = "123")
    @DisplayName("admins can request a user detail")
    public void getUserByIdSuccess() throws Exception {
        String email = "user@mail.com";
        UserEntity userEntity = getUser(1L, email);

        when(mockUserRepository.findById(eq(1L))).thenReturn(Optional.of(userEntity));
        when(mockUserRepository.findByEmail(eq("admin@mail.com"))).thenReturn(userEntity);

        checkExpectedUser(email, 1);
    }

    @Test
    @WithMockUser(authorities = {"user", "2"}, username = "user@mail.com", password = "123")
    @DisplayName("non admin users can't request details of other users")
    public void getUserByIdSuccessUser() throws Exception {
        String email = "user@mail.com";
        UserEntity userEntity = getUser(2L, email);

        when(mockUserRepository.findByEmail(eq(email))).thenReturn(userEntity);

        checkExpectedUser(email, 2);
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "2"}, username = "admin@mail.com", password = "123")
    @DisplayName("should return not found when requesting a non existing user")
    public void getUserByIdFail() throws Exception {
        when(mockUserRepository.findById(eq(1L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/1")).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = {"user"}, username = "user@mail.com", password = "123")
    @DisplayName("non admin users can't delete other users")
    public void deleteUserByUserIsForbidden() throws Exception {
        mockMvc.perform(delete("/users/1")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"}, username = "admin@mail.com", password = "123")
    @DisplayName("admins can delete users")
    public void deleteUserByAdminIsAllowed() throws Exception {
        when(mockUserRepository.findById(eq(1L))).thenReturn(Optional.of(getUser()));
        mockMvc.perform(delete("/users/1")).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"}, username = "admin@mail.com", password = "123")
    @DisplayName("trying to delete a non existing user returns not found")
    public void deleteUserByAdminNotFound() throws Exception {
        when(mockUserRepository.findById(eq(1L))).thenReturn(Optional.empty());
        mockMvc.perform(delete("/users/1")).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = {"USER"}, username = "user@mail.com", password = "123")
    @DisplayName("should return a list of users")
    public void getListOfUsers() throws Exception {
        when(mockUserRepository.findAll()).thenReturn(List.of(
                UserEntity.builder().id(1L).email("userone@mail.com").role(RoleEntity.builder().id(1L).build()).build(),
                UserEntity.builder().id(2L).email("usertwo@mail.com").role(RoleEntity.builder().id(1L).build()).build()
        ));
        mockMvc.perform(get("/users")).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].email", Is.is("userone@mail.com")))
                .andExpect(jsonPath("$[0].id", Is.is(1)))
                .andExpect(jsonPath("$[1].email", Is.is("usertwo@mail.com")))
                .andExpect(jsonPath("$[1].id", Is.is(2)));
    }

    private void checkExpectedUser(String email, int id) throws Exception {
        mockMvc.perform(get("/users/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(id)))
                .andExpect(jsonPath("$.email", Is.is(email)))
                .andExpect(jsonPath("$.role.name", Is.is("USER")))
                .andExpect(jsonPath("$.role.id", Is.is(1)));
    }

    private UserEntity getUser(Long id, String email) {
        return UserEntity.builder()
                .id(id)
                .email(email)
                .role(RoleEntity.builder().id(1L).name("USER").description("User level access").build())
                .build();
    }

    private UserEntity getUser() {
        return UserEntity.builder()
                .id(1L)
                .email("user@mail.com")
                .role(RoleEntity.builder().id(1L).name("USER").description("user level access").build())
                .build();
    }
}
