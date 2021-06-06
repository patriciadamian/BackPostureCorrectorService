package com.degree.back.posture.corrector.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearlyStatsDto {

  private String monthOfYear;
  private int numberOfAlerts;

}
