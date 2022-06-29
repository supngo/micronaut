package com.naturecode.persistence;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;

@Singleton
@Requires(notEnv = Environment.TEST)
public class TestData {
  private final SymbolRepo symbolRepo;
  private final QuoteRepo quoteRepo;
  private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

  public TestData(SymbolRepo symbolRepo, QuoteRepo quoteRepo) {
    this.symbolRepo = symbolRepo;
    this.quoteRepo = quoteRepo;
  }

  @EventListener
  public void init(StartupEvent event) {
    if (symbolRepo.findAll().isEmpty()) {
      Stream.of("APPL", "AMZN", "TSLA")
      .forEach(item -> {
        var symbol = new SymbolEntity();
        symbol.setValue(item);
        symbolRepo.save(symbol);
      });
      // .map(SymbolEntity::new).forEach(symbolRepo::save);
    }

    if (quoteRepo.findAll().isEmpty()) {
      symbolRepo.findAll().forEach(symbol -> {
        var quote = new QuoteEntity();
        quote.setSymbol(symbol);
        quote.setAsk(randomValue());
        quote.setBid(randomValue());
        quote.setLastPrice(randomValue());
        quote.setVolume(randomValue());
        quoteRepo.save(quote);
      });
    }
  }

  private BigDecimal randomValue() {
    return BigDecimal.valueOf(RANDOM.nextDouble(1, 100));
  }
}
