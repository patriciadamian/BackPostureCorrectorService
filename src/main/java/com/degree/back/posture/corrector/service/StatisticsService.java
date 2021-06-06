package com.degree.back.posture.corrector.service;

import static com.degree.back.posture.corrector.ErrorCode.GENERIC_ERROR_CODE;
import static java.util.stream.Collectors.groupingBy;

import com.degree.back.posture.corrector.BpcException;
import com.degree.back.posture.corrector.api.dto.WeeklyStatsDto;
import com.degree.back.posture.corrector.api.dto.YearlyStatsDto;
import com.degree.back.posture.corrector.repository.StatisticsRepository;
import com.degree.back.posture.corrector.repository.entity.StatisticsEntity;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class StatisticsService {

  private final StatisticsRepository statisticsRepository;

  @Transactional
  public StatisticsEntity save(long userId) {
    var newStatisticsEntity = new StatisticsEntity(userId);
    try {
      var result = statisticsRepository.save(newStatisticsEntity);
      log.info("User successfully registered!");
      return result;
    } catch (Exception e) {
      throw new BpcException("Save statistics entity operation failed!", GENERIC_ERROR_CODE);
    }
  }

  @Transactional
  public List<WeeklyStatsDto> getWeeklyStats(long userId) {
    try {
      var allStatistics = statisticsRepository.getByUserId(userId);
      var currentWeekAlerts = allStatistics.stream()
          .filter(d -> isDateInCurrentWeek(d.getCorrectionAlertDate()))
          .collect(groupingBy(d -> d.getCorrectionAlertDate().getDayOfWeek()));
      List<WeeklyStatsDto> result = new ArrayList<>();
      for (var entry : currentWeekAlerts.entrySet()) {
        result.add(new WeeklyStatsDto(entry.getKey().toString(), entry.getValue().size()));
      }

      return result;
    } catch (Exception e) {
      throw new BpcException("Get weekly statistics operation failed!", GENERIC_ERROR_CODE);
    }
  }

  @Transactional
  public List<YearlyStatsDto> getYearlyStats(long userId) {
    try {
      var allStatistics = statisticsRepository.getByUserId(userId);
      var currentWeekAlerts = allStatistics.stream()
          .filter(d -> isDateInCurrentYear(d.getCorrectionAlertDate()))
          .collect(groupingBy(d -> d.getCorrectionAlertDate().getMonth()));
      List<YearlyStatsDto> result = new ArrayList<>();
      for (var entry : currentWeekAlerts.entrySet()) {
        result.add(new YearlyStatsDto(entry.getKey().toString(), entry.getValue().size()));
      }

      return result;
    } catch (Exception e) {
      throw new BpcException("Get yearly statistics operation failed!", GENERIC_ERROR_CODE);
    }

  }

  private boolean isDateInCurrentWeek(LocalDateTime localDateTime) {
    ZoneId defaultZoneId = ZoneId.systemDefault();
    Date date = Date.from(localDateTime.toLocalDate().atStartOfDay(defaultZoneId).toInstant());

    Calendar currentCalendar = Calendar.getInstance();
    int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
    int year = currentCalendar.get(Calendar.YEAR);
    Calendar targetCalendar = Calendar.getInstance();
    targetCalendar.setTime(date);
    int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
    int targetYear = targetCalendar.get(Calendar.YEAR);

    boolean belongs = week == targetWeek && year == targetYear;
    return belongs;
  }

  private boolean isDateInCurrentYear(LocalDateTime localDateTime) {
    ZoneId defaultZoneId = ZoneId.systemDefault();
    Date date = Date.from(localDateTime.toLocalDate().atStartOfDay(defaultZoneId).toInstant());

    Calendar currentCalendar = Calendar.getInstance();
    int year = currentCalendar.get(Calendar.YEAR);
    Calendar targetCalendar = Calendar.getInstance();
    targetCalendar.setTime(date);
    int targetYear = targetCalendar.get(Calendar.YEAR);

    boolean belongs = year == targetYear;
    return belongs;
  }

}
