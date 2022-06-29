package com.naturecode;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
  info = @Info(
    title = "naturecode stock",
    version = "1.0",
    description = "Nature Code using Micronaut",
    license = @License(name = "MIT")
  )
)
public class Application {
  public static void main(String[] args) {
    Micronaut.run(Application.class, args);
  }
}
