package com.degree.back.posture.corrector.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeeklyStatsDto {

  private String dayOfWeek;
  private int numberOfAlerts;

}
