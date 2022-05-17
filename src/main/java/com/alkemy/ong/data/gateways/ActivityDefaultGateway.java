package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.ActivityEntity;
import com.alkemy.ong.data.repositories.ActivityRepository;
import com.alkemy.ong.domain.activities.Activity;
import com.alkemy.ong.domain.activities.ActivityGateway;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class ActivityDefaultGateway implements ActivityGateway {
  ActivityRepository activityRepository;

  public ActivityDefaultGateway(ActivityRepository activityRepository) {
    this.activityRepository = activityRepository;
  }

  @Override
  public Activity save(Activity activity) {
    ActivityEntity savedActivityEntity = activityRepository.save(toEntity(activity));

    return toModel(savedActivityEntity);
  }

  @Override
  public Activity update(Long id, Activity activity) {
    ActivityEntity foundActivity = activityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("activity with id " + id));
    refreshValues(foundActivity, activity);
    ActivityEntity savedActivity = activityRepository.save(foundActivity);
    return toModel(savedActivity);
  }

  private void refreshValues(ActivityEntity activityEntity, Activity activity) {
    activityEntity.setName(activity.getName());
    activityEntity.setContent(activity.getContent());
    activityEntity.setImage(activity.getImage());
  }

  private Activity toModel(ActivityEntity savedActivityEntity) {
    return Activity.builder()
        .id(savedActivityEntity.getId())
        .name(savedActivityEntity.getName())
        .content(savedActivityEntity.getContent())
        .image(savedActivityEntity.getImage())
        .build();
  }

  private ActivityEntity toEntity(Activity activity) {
    return ActivityEntity.builder()
        .id(activity.getId())
        .name(activity.getName())
        .content(activity.getContent())
        .image(activity.getImage())
        .build();
  }
}
