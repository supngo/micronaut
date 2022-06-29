package com.naturecode.persistence;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "quote")
@Table(name = "quotes", schema = "mn")
@Data
public class QuoteEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(targetEntity = SymbolEntity.class)
  @JoinColumn(name = "symbol", referencedColumnName = "id")
  private SymbolEntity symbol;

  private BigDecimal bid;
  private BigDecimal ask;
  private BigDecimal volume;

  @Column(name = "last_price")
  private BigDecimal lastPrice;
}
