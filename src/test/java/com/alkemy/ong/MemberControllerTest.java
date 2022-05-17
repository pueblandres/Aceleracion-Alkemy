package com.alkemy.ong;


import com.alkemy.ong.data.entities.MemberEntity;
import com.alkemy.ong.data.repositories.MembersRepository;
import com.alkemy.ong.domain.utils.PageModel;
import com.alkemy.ong.web.controllers.MemberController.MemberDTO;
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


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MembersRepository membersRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(authorities = "ADMIN")
    void when_callSaveMethod_then_returnsMemberEntity() throws Exception {

        when(membersRepository.save(member(null))).thenReturn(member(1L));

        mvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member(1L))))
                .andExpect(jsonPath("$", aMapWithSize(7)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("FranciscoE")))
                .andExpect(jsonPath("$.facebookUrl", is("facebookE")))
                .andExpect(jsonPath("$.instagramUrl", is("instagramE")))
                .andExpect(jsonPath("$.linkedinUrl", is("linkedinE")))
                .andExpect(jsonPath("$.image", is("imageE")))
                .andExpect(jsonPath("$.description", is("descriptionE")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(membersRepository, times(1)).save(member(null));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void findByPage() throws Exception {
        List<MemberEntity> memberEntities = Arrays.asList(member(1L), member(1L), member(1L));

        PageModel<MemberEntity> pageModel =
                PageModel.<MemberEntity>builder()
                        .content(memberEntities)
                        .build();

        when(membersRepository.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(pageModel.getContent()));

        mvc.perform(get("/members").param("page", "0").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pageModel)))
                .andExpect(jsonPath("$.content.[0].id", is(1)))
                .andExpect(jsonPath("$.content.[1].id", is(1)))
                .andExpect(jsonPath("$.content.[2].id", is(1)))
                .andExpect(jsonPath("$.content.[0].name", is("FranciscoE")))
                .andExpect(jsonPath("$.content.[1].name", is("FranciscoE")))
                .andExpect(jsonPath("$.content.[2].name", is("FranciscoE")))
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void when_callDeleteMethod_then_returnsIsNoContent() throws Exception {

        when(membersRepository.findById(member(1L).getId())).thenReturn(Optional.of(member(1L)));
        when(membersRepository.save(member(1L))).thenReturn(member(1L));

        mvc.perform(delete("/members/1")).andExpect(status().isNoContent());

    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void when_callDeleteMethodUnregisteredId_then_returnsNotFound() throws Exception {
        when(membersRepository.findById(2L)).thenReturn(Optional.empty());
        mvc.perform(delete("/members/2")).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void when_updateMember_then_returnIsOk() throws Exception {

        when(membersRepository.findById(member(1L).getId())).thenReturn(Optional.of(member(1L)));
        when(membersRepository.save(member(1L))).thenReturn(member(1L));

        mvc.perform(put("/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member(1L))))
                .andExpect(jsonPath("$", aMapWithSize(7)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("FranciscoE")))
                .andExpect(jsonPath("$.facebookUrl", is("facebookE")))
                .andExpect(jsonPath("$.instagramUrl", is("instagramE")))
                .andExpect(jsonPath("$.linkedinUrl", is("linkedinE")))
                .andExpect(jsonPath("$.image", is("imageE")))
                .andExpect(jsonPath("$.description", is("descriptionE")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(membersRepository, times(1)).save(member(1L));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void when_isMemberWhitMissingAttributes_then_returnBadRequest() throws Exception {

        mvc.perform(put("/members/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO(null))))
                .andExpect(status().isBadRequest());
    }

    private MemberEntity member (Long id){
        return MemberEntity.builder()
                .id(id).name("FranciscoE").facebookUrl("facebookE").instagramUrl("instagramE")
                .linkedinUrl("linkedinE").image("imageE").description("descriptionE").isDeleted(false).build();
    }

    private MemberDTO memberDTO(String name){
        return MemberDTO.builder()
                .name(name).facebookUrl("facebook").instagramUrl("instagram")
                .linkedinUrl("linkedin").image("image").description("description").build();
    }
}