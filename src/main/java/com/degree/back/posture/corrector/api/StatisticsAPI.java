package com.degree.back.posture.corrector.api;

import static org.springframework.http.HttpStatus.CREATED;

import com.degree.back.posture.corrector.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bpc/statistics")
public class StatisticsAPI {

  private final StatisticsService statisticsService;

  public StatisticsAPI(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  @PostMapping(path = "/{id}")
  public ResponseEntity save(@PathVariable("id") long id) {
    var result = statisticsService.save(id);
    return ResponseEntity.status(CREATED).body(result);
  }

  @GetMapping(path = "/week/{id}")
  public ResponseEntity getWeeklyStatistics(@PathVariable("id") long id) {
    var result = statisticsService.getWeeklyStats(id);
    return ResponseEntity.ok().body(result);
  }

  @GetMapping(path = "/year/{id}")
  public ResponseEntity getYearlyStatistics(@PathVariable("id") long id) {
    var result = statisticsService.getYearlyStats(id);
    return ResponseEntity.ok().body(result);
  }

}
