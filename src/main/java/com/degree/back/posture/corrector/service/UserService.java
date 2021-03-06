package com.degree.back.posture.corrector.service;

import static com.degree.back.posture.corrector.ErrorCode.ENTITY_NOT_FOUND;
import static com.degree.back.posture.corrector.ErrorCode.GENERIC_ERROR_CODE;
import static com.degree.back.posture.corrector.ErrorCode.LOGIN_ERROR;

import com.degree.back.posture.corrector.BpcException;
import com.degree.back.posture.corrector.api.dto.LoginDto;
import com.degree.back.posture.corrector.api.dto.RegisterDto;
import com.degree.back.posture.corrector.api.dto.TokenDto;
import com.degree.back.posture.corrector.api.dto.UserDto;
import com.degree.back.posture.corrector.api.dto.UserSensorsDto;
import com.degree.back.posture.corrector.repository.UserRepository;
import com.degree.back.posture.corrector.repository.entity.UserEntity;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public UserEntity register(RegisterDto registerDto) {
    try {
      var result = userRepository.save(registerDto.toUserEntity());
      log.info("User successfully registered!");
      return result;
    } catch (Exception e) {
      throw new BpcException("Register operation failed!", GENERIC_ERROR_CODE);
    }
  }

  @Transactional
  public TokenDto login(LoginDto loginDto) {
    try {
      var result = userRepository.findByEmail(loginDto.getEmail());
      if (result.isEmpty()) {
        throw new BpcException("User with email " + loginDto.getEmail() + " not found in db!",
            LOGIN_ERROR);
      } else {
        var user = result.get();
        if (user.getPassword().equals(loginDto.getPassword())) {
          return new TokenDto(JwtService.create(user));
        } else {
          throw new BpcException("Email and password combination is not correct", LOGIN_ERROR);
        }
      }
    } catch (Exception e) {
      throw new BpcException(e.getMessage(), GENERIC_ERROR_CODE);
    }
  }

  @Transactional
  public List<UserEntity> getAllUsers() {
    return userRepository.getAll();
  }

  @Transactional
  public UserEntity findById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(
            () -> new BpcException("User with id " + id + " not found!", ENTITY_NOT_FOUND));
  }

  @Transactional
  public UserEntity update(UserDto userDto) {
    try {
      var id = userDto.getId();
      userRepository.updateProfile(id, userDto.getAge(), userDto.getHeight(), userDto.getWeight());
      log.info("User successfully updated!");
      return userRepository.findById(id).orElseThrow(
          () -> new BpcException("User with id " + id + " not found!", ENTITY_NOT_FOUND));

    } catch (Exception e) {
      throw new BpcException("Update operation failed!", GENERIC_ERROR_CODE);
    }
  }

  @Transactional
  public UserEntity updateSensors(UserSensorsDto userSensorsDto) {
    try {
      var id = userSensorsDto.getId();
      userRepository.updateSensors(id,
          userSensorsDto.getAccelerometerX(),
          userSensorsDto.getAccelerometerY(),
          userSensorsDto.getAccelerometerZ(),
          userSensorsDto.getGyroscopeX(),
          userSensorsDto.getGyroscopeY(),
          userSensorsDto.getGyroscopeZ(),
          userSensorsDto.getMagnetometerX(),
          userSensorsDto.getMagnetometerY(),
          userSensorsDto.getMagnetometerZ());
      log.info("User successfully updated!");
      return userRepository.findById(id).orElseThrow(
          () -> new BpcException("User with id " + id + " not found!", ENTITY_NOT_FOUND));

    } catch (Exception e) {
      throw new BpcException("Update operation failed!", GENERIC_ERROR_CODE);
    }
  }

  @Transactional
  public void delete(Long id) {
    try {
      userRepository.deleteById(id);
    } catch (Exception e) {
      throw new BpcException("Update operation failed!", GENERIC_ERROR_CODE);
    }
  }

}
