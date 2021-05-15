package com.degree.back.posture.corrector.api;

import com.degree.back.posture.corrector.service.JwtService;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class RequestsFilter implements Filter {

  private static final String AUTHORIZATION = "authorization";

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

    Map<String, String> headers = Collections.list(httpRequest.getHeaderNames())
        .stream()
        .collect(Collectors.toMap(String::toLowerCase, httpRequest::getHeader));

    if (!headers.containsKey(AUTHORIZATION)) {
      ((HttpServletResponse) servletResponse)
          .sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization header missing");
    } else if (!JwtService.isValid(headers.get(AUTHORIZATION))) {
      ((HttpServletResponse) servletResponse)
          .sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
    } else {
      filterChain.doFilter(servletRequest, servletResponse);
    }

  }

}
