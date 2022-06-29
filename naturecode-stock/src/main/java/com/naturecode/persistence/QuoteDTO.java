package com.naturecode.persistence;

import java.math.BigDecimal;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

@Introspected
@Setter
@Getter
public class QuoteDTO {
  private Integer id;
  private BigDecimal volume;
}