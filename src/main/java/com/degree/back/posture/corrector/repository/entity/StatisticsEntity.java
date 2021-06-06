package com.degree.back.posture.corrector.repository.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(schema = "back_posture.corrector", name = "statistics")
@NamedQuery(name = "StatisticsEntity.getByUserId", query = "Select s from StatisticsEntity s where s.userId = :ID")
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "correction_alert")
  private LocalDateTime correctionAlertDate;

  @Column(name = "user_id")
  private Long userId;

  public StatisticsEntity(Long userId) {
    this.correctionAlertDate = LocalDateTime.now();
    this.userId = userId;
  }
}
