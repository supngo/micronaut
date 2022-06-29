package com.naturecode.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import com.github.javafaker.Faker;
import com.naturecode.model.Symbol;

import jakarta.inject.Singleton;

@Singleton
public class InMemStore {
  private final Map<String, Symbol> symbols = new HashMap<>();
  private final Faker faker = new  Faker();

  @PostConstruct
  public void initialize() {
    initializeWith(10);
  }

  public void initializeWith(int num) {
    symbols.clear();
    IntStream.range(0, num).forEach(i -> {
      addNewSymbol();
    });
  }

  private void addNewSymbol() {
    var symbol = new Symbol(faker.stock().nsdqSymbol());
    symbols.put(symbol.value(), symbol);
  }

  public Map<String, Symbol> getSymbols() {
    return symbols;
  }
}
