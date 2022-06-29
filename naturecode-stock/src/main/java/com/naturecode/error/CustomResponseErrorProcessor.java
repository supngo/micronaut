package com.naturecode.error;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Singleton;

@Singleton
public class CustomResponseErrorProcessor implements ErrorResponseProcessor<CustomError> {

  @Override
  public MutableHttpResponse<CustomError> processResponse(
    @NonNull ErrorContext errorContext,
    @NonNull MutableHttpResponse<?> baseResponse) {
    CustomError customError;
    if(!errorContext.hasErrors()) {
      customError = new CustomError(
        baseResponse.getStatus().getCode(),
        baseResponse.getStatus().name(),
        "No custom Error found!"
      );
    } else {
      var error = errorContext.getErrors().get(0);
      customError = new CustomError(
        baseResponse.getStatus().getCode(),
        baseResponse.getStatus().name(),
        error.getMessage()
      );
    }
    return baseResponse.body(customError).contentType(MediaType.APPLICATION_JSON);
  }
}
