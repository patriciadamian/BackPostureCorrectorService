package com.degree.back.posture.corrector.repository;

import com.degree.back.posture.corrector.repository.entity.StatisticsEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends CrudRepository<StatisticsEntity, Long> {

  List<StatisticsEntity> getByUserId(@Param("ID") long userId);

}
