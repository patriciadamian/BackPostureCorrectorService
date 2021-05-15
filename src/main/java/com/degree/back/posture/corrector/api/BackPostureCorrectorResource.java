package com.degree.back.posture.corrector.api;

import com.degree.back.posture.corrector.api.dto.LoginDto;
import com.degree.back.posture.corrector.api.dto.RegisterDto;
import com.degree.back.posture.corrector.repository.entity.UserEntity;
import com.degree.back.posture.corrector.service.UserService;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bpc")
public class BackPostureCorrectorResource {

  private final UserService userService;

  public BackPostureCorrectorResource(
      UserService userService) {
    this.userService = userService;
  }

  @PostMapping(path = "/register")
  public ResponseEntity register(@RequestBody RegisterDto registerDto) {
    try {
      var result = userService.register(registerDto);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      var message = e.getMessage();
      if (e instanceof DataIntegrityViolationException) {
        message = "Email must be unique!";
      }
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
  }

  @PostMapping(path = "/login")
  public ResponseEntity login(@RequestBody LoginDto loginDto) {
    try {
      var result = userService.login(loginDto);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
  }

  @GetMapping(path = "/users/")
  public ResponseEntity<List<UserEntity>> getAllUsers() {
    var result = userService.getAllUsers();
    return ResponseEntity.ok().body(result);
  }

}
