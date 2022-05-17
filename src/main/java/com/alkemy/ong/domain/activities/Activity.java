package com.alkemy.ong.domain.activities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Activity {
  Long id;
  String name;
  String content;
  String image;
}
