package com.naturecode.error;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
// @Requires(classes = {CustomException.class, ExceptionHandler.class})
public class CustomExceptionHandler implements ExceptionHandler<CustomException, HttpResponse<CustomError>> {
  @Override
  public HttpResponse<CustomError> handle(HttpRequest request, CustomException exception) {
    return HttpResponse.badRequest(new CustomError(
      HttpStatus.BAD_REQUEST.getCode(),
      "WatchList is empty",
      String.format("Please pass a non empty WatchList")  
    ));
  }
}
