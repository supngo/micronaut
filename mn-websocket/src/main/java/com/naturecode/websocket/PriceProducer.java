package com.naturecode.websocket;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import io.micronaut.scheduling.annotation.Scheduled;
import io.micronaut.websocket.WebSocketBroadcaster;
import jakarta.inject.Singleton;

@Singleton
public class PriceProducer {

  private final WebSocketBroadcaster boardcaster;

  public PriceProducer(final WebSocketBroadcaster boardcaster) {
    this.boardcaster = boardcaster;
  }

  @Scheduled(fixedDelay = "3s")
  public void produce() {
    boardcaster.broadcastSync(new Price("AMZN", randomValue()));
  }
  
  private BigDecimal randomValue() {
    return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1000, 2000));
  }
}
