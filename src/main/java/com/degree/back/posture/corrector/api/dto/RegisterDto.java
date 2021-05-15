package com.degree.back.posture.corrector.api.dto;

import com.degree.back.posture.corrector.repository.entity.UserEntity;
import lombok.Data;

@Data
public class RegisterDto {

  private String firstName;
  private String lastName;
  private String email;
  private String password;

  public UserEntity toUserEntity() {
    return new UserEntity(firstName, lastName, email, password);
  }

}
