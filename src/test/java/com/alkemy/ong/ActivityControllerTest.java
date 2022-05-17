package com.alkemy.ong;

import com.alkemy.ong.data.entities.ActivityEntity;
import com.alkemy.ong.data.repositories.ActivityRepository;
import io.swagger.v3.core.util.Json;
import org.hamcrest.core.Is;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static com.alkemy.ong.web.controllers.ActivityController.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DisplayName("Given a user controller")
public class ActivityControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ActivityRepository mockActivityRepository;

    @Test
    @WithMockUser(authorities = {"ADMIN", "2"}, username = "admin@mail.com", password = "123")
    @DisplayName("admins should be able to create activities")
    public void createActivitySuccess() throws Exception {
        String content = "Activity Content";
        String name = "Activity Name";
        String image = "https://bucket.com/image.jpg";

        ActivityDto activityDto = getActivityDto(content, name, image);

        when(mockActivityRepository.save(getActivityEntity(null, content, name, image))).thenReturn(getActivityEntity(1L, content, name, image));

        ResultActions resultAction = performHttpAction(post("/activities"), activityDto);
        checkResponseActivity(status().isCreated(), resultAction, 1, content, name, image);
    }

    @Test
    @WithMockUser(authorities = {"USER", "2"}, username = "user@mail.com", password = "123")
    @DisplayName("non admin users shouldn't be able to create activities")
    public void createActivityFailure() throws Exception {
        ActivityDto activityDto = getActivityDto("Activity Content", "Activity Name", "https://bucket.com/image.jpg");
        performHttpAction(post("/activities"), activityDto).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "2"}, username = "admin@mail.com", password = "123")
    @DisplayName("trying to create a invalid activity should return error")
    public void createInvalidActivityByAdmin() throws Exception {
        ActivityDto activityDto = getActivityDto("Activity Content", "", "https://bucket.com/image.jpg");
        performHttpAction(post("/activities"), activityDto).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "2"}, username = "admin@mail.com", password = "123")
    @DisplayName("admins should be able to update activities")
    public void updateActivitySuccess() throws Exception {

        String contentAfter = "After activity content";
        String imageAfter = "https://bucket.com/after.jpg";
        String nameAfter = "After activity name";

        ActivityDto activityDto = getActivityDto(1L, contentAfter, imageAfter, nameAfter);

        when(mockActivityRepository.findById(eq(1L))).thenReturn(Optional.of(
                getActivityEntity(
                        activityDto.getId(),
                        "Before activity content",
                        "https://bucket.com/before.jpg", "Before activity name"
                )));

        when(mockActivityRepository.save(getActivityEntity(1L, contentAfter, nameAfter, imageAfter))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        ResultActions perform = performHttpAction(put("/activities/1"), activityDto);
        checkResponseActivity(status().isOk(), perform, 1, contentAfter, nameAfter, imageAfter);
    }

    @Test
    @WithMockUser(authorities = {"USER", "2"}, username = "user@mail.com", password = "123")
    @DisplayName("non admin users shouldn't be able to update activities")
    public void updateActivityNonAdminFail() throws Exception {
        performHttpAction(put("/activities/1"), getActivityDto(1L, "name", "content", "image"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "2"}, username = "user@mail.com", password = "123")
    @DisplayName("should return bad request when trying to update an activity with missing fields in the request")
    public void nose() throws Exception {
        performHttpAction(put("/activities/1"), getActivityDto(1L, "", "image", "name"))
                .andExpect(status().isBadRequest());
    }

    private ResultActions performHttpAction(MockHttpServletRequestBuilder httpAction, ActivityDto activityDto) throws Exception {
        return mockMvc.perform(httpAction.contentType(MediaType.APPLICATION_JSON).content(Json.mapper().writeValueAsString(activityDto)));
    }

    public void checkResponseActivity(ResultMatcher expectedStatus, ResultActions resultActions, Integer id, String content, String name, String image) throws Exception {
        resultActions
                .andExpect(expectedStatus)
                .andExpect(jsonPath("$.id", Is.is(id)))
                .andExpect(jsonPath("$.content", Is.is(content)))
                .andExpect(jsonPath("$.name", Is.is(name)))
                .andExpect(jsonPath("$.image", Is.is(image)));
    }

    private ActivityDto getActivityDto(Long id, String contentAfter, String imageAfter, String nameAfter) {
        return ActivityDto.builder()
                .id(id)
                .content(contentAfter)
                .image(imageAfter)
                .name(nameAfter)
                .build();
    }

    private ActivityEntity getActivityEntity(Long id, String content, String name, String image) {
        return ActivityEntity.builder()
                .id(id)
                .content(content)
                .name(name)
                .image(image)
                .build();
    }

    private ActivityDto getActivityDto(String content, String name, String image) {
        return ActivityDto.builder()
                .content(content)
                .name(name)
                .image(image)
                .build();
    }
}
