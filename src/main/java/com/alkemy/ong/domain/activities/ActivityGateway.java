package com.alkemy.ong.domain.activities;

public interface ActivityGateway {
  Activity save(Activity activity);

  Activity update(Long id, Activity activity);
}
