package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.activities.Activity;
import com.alkemy.ong.domain.activities.ActivityService;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/activities")
public class ActivityController {

  private final ActivityService activityService;

  @Autowired
  public ActivityController(ActivityService activityService) {
    this.activityService = activityService;
  }

  @PostMapping
  public ResponseEntity<ActivityDto> save(@Valid @RequestBody ActivityDto activityDto) {
    Activity saveActivity = activityService.save(toModel(activityDto));

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(toDto(saveActivity));
  }

  @PutMapping("{id}")
  public ResponseEntity<ActivityDto> update(@PathVariable Long id, @Valid @RequestBody ActivityDto activityDto) {
    Activity updateActivity = activityService.update(id, toModel(activityDto));

    return ResponseEntity.ok().body(toDto(updateActivity));
  }

  private Activity toModel(ActivityDto activityDto) {
    return Activity
        .builder()
        .id(activityDto.getId())
        .name(activityDto.getName())
        .content(activityDto.getContent())
        .image(activityDto.getImage())
        .build();
  }

  private ActivityDto toDto(Activity saveActivity) {
    return ActivityDto.builder()
        .name(saveActivity.getName())
        .id(saveActivity.getId())
        .content(saveActivity.getContent())
        .image(saveActivity.getImage())
        .build();
  }

  @Builder
  @Data
  public static class ActivityDto {
    Long id;

    @NotBlank(message = "Name is required")
    String name;

    @NotBlank(message = "Content is required")
    String content;

    @NotBlank(message = "Image is required")
    String image;
  }

}
