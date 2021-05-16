package com.degree.back.posture.corrector.api;

import com.degree.back.posture.corrector.api.dto.LoginDto;
import com.degree.back.posture.corrector.api.dto.RegisterDto;
import com.degree.back.posture.corrector.api.dto.TokenDto;
import com.degree.back.posture.corrector.repository.entity.UserEntity;
import com.degree.back.posture.corrector.service.UserService;
import java.util.List;
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
  public ResponseEntity<UserEntity> register(@RequestBody RegisterDto registerDto) {
    var result = userService.register(registerDto);
    return ResponseEntity.ok().body(result);
  }

  @PostMapping(path = "/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
    var result = userService.login(loginDto);
    return ResponseEntity.ok().body(result);
  }

  @GetMapping(path = "/users/")
  public ResponseEntity<List<UserEntity>> getAllUsers() {
    var result = userService.getAllUsers();
    return ResponseEntity.ok().body(result);
  }

}
