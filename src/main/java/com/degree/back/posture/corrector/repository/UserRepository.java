package com.degree.back.posture.corrector.repository;

import com.degree.back.posture.corrector.repository.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

  List<UserEntity> getAll();

  Optional<UserEntity> findById(Long id);

  Optional<UserEntity> findByEmail(@Param("EMAIL") String email);

  @Modifying
  void updateProfile(@Param(value = "ID") Long id,
      @Param(value = "AGE") Short age,
      @Param(value = "HEIGHT") Short height,
      @Param(value = "WEIGHT") Short weight);

  @Modifying
  void updateSensors(@Param(value = "ID") Long id,
      @Param(value = "AccelerometerX") String accelerometerX,
      @Param(value = "AccelerometerY") String accelerometerY,
      @Param(value = "AccelerometerZ") String accelerometerZ,
      @Param(value = "GyroscopeX") String gyroscopeX,
      @Param(value = "GyroscopeY") String gyroscopeY,
      @Param(value = "GyroscopeZ") String gyroscopeZ,
      @Param(value = "MagnetometerX") String magnetometerX,
      @Param(value = "MagnetometerY") String magnetometerY,
      @Param(value = "MagnetometerZ") String magnetometerZ);
}
