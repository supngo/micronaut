package com.naturecode.websocket;

import java.math.BigDecimal;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Introspected
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {
  private String symbol;
  private BigDecimal price;
}
