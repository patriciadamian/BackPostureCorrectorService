package com.degree.back.posture.corrector.api.dto;

import lombok.Data;

@Data
public class UserSensorsDto {

  private Long id;
  private String accelerometerX;
  private String accelerometerY;
  private String accelerometerZ;
  private String gyroscopeX;
  private String gyroscopeY;
  private String gyroscopeZ;
  private String magnetometerX;
  private String magnetometerY;
  private String magnetometerZ;

}
