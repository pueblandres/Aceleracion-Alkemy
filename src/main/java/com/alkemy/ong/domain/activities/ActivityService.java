package com.alkemy.ong.domain.activities;

import org.springframework.stereotype.Service;

@Service
public class ActivityService {

  private final ActivityGateway activityGateway;

  public ActivityService(ActivityGateway activityGateway) {
    this.activityGateway = activityGateway;
  }

  public Activity save(Activity activity) {
    return activityGateway.save(activity);
  }

  public Activity update(Long id, Activity activity) {
    return activityGateway.update(id, activity);
  }
}
