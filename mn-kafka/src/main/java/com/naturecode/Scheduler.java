package com.naturecode;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.extern.java.Log;

@Singleton
@Log
public class Scheduler {
  private final QuoteProducer producer;
  private static final List<String> SYMBOLS = Arrays.asList("APPL", "AMZN", "FB", "GOOD", "NFLX");

  public Scheduler(final QuoteProducer producer) {
    this.producer = producer;
  }

  @Scheduled(fixedDelay = "10s")
  void generate() {
    final ThreadLocalRandom random = ThreadLocalRandom.current();
    final Quote quote = new Quote(
      SYMBOLS.get(random.nextInt(0, SYMBOLS.size() -1 )),
      randomValue(random),
      randomValue(random)
    );
    log.info(quote.toString());
    producer.send(quote.getSymbol(), quote);
  }

  private BigDecimal randomValue(ThreadLocalRandom random) {
    return BigDecimal.valueOf(random.nextDouble(0, 1000));
  }
}
