package com.degree.back.posture.corrector.infrastructure;

import com.degree.back.posture.corrector.api.RequestsFilter;
import com.degree.back.posture.corrector.repository.StatisticsRepository;
import com.degree.back.posture.corrector.repository.UserRepository;
import com.degree.back.posture.corrector.service.StatisticsService;
import com.degree.back.posture.corrector.service.UserService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextBeans {

  @Bean
  public UserService userService(UserRepository userRepository) {
    return new UserService(userRepository);
  }

  @Bean
  public StatisticsService statisticsService(StatisticsRepository statisticsRepository) {
    return new StatisticsService(statisticsRepository);
  }

  @Bean
  public FilterRegistrationBean<RequestsFilter> requestsFilterFilterRegistrationBean(){
    FilterRegistrationBean<RequestsFilter> registrationBean
        = new FilterRegistrationBean<>();

    registrationBean.setFilter(new RequestsFilter());
    registrationBean.addUrlPatterns("/bpc/users/*");

    return registrationBean;
  }

}
