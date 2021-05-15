package com.degree.back.posture.corrector.service;


import com.degree.back.posture.corrector.BpcException;
import com.degree.back.posture.corrector.api.dto.LoginDto;
import com.degree.back.posture.corrector.api.dto.RegisterDto;
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
      throw new BpcException("Register operation failed!");
    }
  }

  @Transactional
  public String login(LoginDto loginDto) {
    try {
      var result = userRepository.findByEmail(loginDto.getEmail());
      if (result.isEmpty()) {
        throw new BpcException("User with email " + loginDto.getEmail() + " not found in db!");
      } else {
        var user = result.get();
        if (user.getPassword().equals(loginDto.getPassword())) {
          return JwtService.create(user);
        } else {
          throw new BpcException("Email and password combination is not correct");
        }
      }
    } catch (Exception e) {
      throw new BpcException(e.getMessage());
    }
  }

  @Transactional
  public List<UserEntity> getAllUsers() {
    return userRepository.getAll();
  }

  @Transactional
  public UserEntity findById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new BpcException("User with id " + id + " not found!"));
  }
}
