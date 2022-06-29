package com.naturecode.error;

import com.naturecode.model.RestApiResponse;

public record CustomError (
  int status,
  String error,
  String message
) implements RestApiResponse {
}