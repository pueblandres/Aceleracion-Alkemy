package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.RoleEntity;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.UserRepository;
import com.alkemy.ong.web.security.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void when_successfulLogin_then_jwtToken() throws Exception {
        getResult("1234", post("/auth/login")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void when_failedLogin_then_internalServerError() throws Exception {
        getResult("123", post("/auth/login")).andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    void when_callGetAuthenticatedUser_then_isOk() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        MvcResult mvcResult = getResult("1234", post("/auth/login")).andReturn();
        var authenticationResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserController.AuthenticationResponse.class);

        mvc.perform(get("/auth/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationResponse.getJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUserEntity())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(153)))
                .andExpect(jsonPath("$.email", is("lucas0@gmail.com")))
                .andExpect(jsonPath("$.role.id", is(2)))
                .andExpect(jsonPath("$.role.name", is("user")))
                .andExpect(jsonPath("$.role.description", is("User level access")));
    }

    private ResultActions getResult(String pass, MockHttpServletRequestBuilder requestBuilder) throws Exception {
        String name = "lucas0@gmail.com";
        when(userRepository.findByEmail(eq(name))).thenReturn(getUserEntity());

        return mvc.perform(requestBuilder
                        .param("username", "lucas0@gmail.com")
                        .param("password", pass)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private UserEntity getUserEntity() {
        return UserEntity.builder()
                .id(153L)
                .email("lucas0@gmail.com")
                .password("$2a$10$v7zOTN4BJrgeGsEIxk9ikOjaQEDFHDTpohmYov8ZEbn1I9jZNwPSe")
                .role(
                        RoleEntity.builder()
                                .id(2L)
                                .name("user")
                                .description("User level access").build()
                )
                .build();
    }

    private UsernamePasswordAuthenticationToken getAuthReq() {
        UserDetails userDetails = getCustomUserDetails(getUserEntity());
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        return authReq;
    }

    private UserDetails getCustomUserDetails(UserEntity userEntity) {
        return new CustomUserDetails(userEntity.getEmail(), userEntity.getPassword(), userEntityRole2Collection(userEntity), userEntity.getId());
    }

    private Collection<? extends GrantedAuthority> userEntityRole2Collection(UserEntity userEntity) {
        return Optional.ofNullable(userEntity).stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName().toUpperCase()))
                .collect(toList());
    }
}