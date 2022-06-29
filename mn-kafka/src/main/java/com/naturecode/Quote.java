package com.naturecode;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quote {
  private String symbol;
  private BigDecimal lastPrice;
  private BigDecimal volume;
}
