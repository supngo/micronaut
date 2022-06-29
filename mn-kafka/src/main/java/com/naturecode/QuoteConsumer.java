package com.naturecode;

import java.util.List;
import java.util.stream.Collectors;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import lombok.extern.java.Log;

@KafkaListener(
  clientId = "consumer",
  groupId = "consumer_group",
  batch = true
)
@Log
public class QuoteConsumer {
  private final BatchProducer batchProducer;

  public QuoteConsumer(final BatchProducer batchProducer) {
    this.batchProducer = batchProducer;
  }

  @Topic("quotes")
  void receive(List<Quote> quote) {
    // log.info("Consume: " + quote);

    // forward to another producer
    final List<Quote> newQuotes = quote.stream().map(item -> 
      new Quote(item.getSymbol(), item.getLastPrice(), item.getVolume())).collect(Collectors.toList()
    );
    batchProducer.send(newQuotes).doOnError(e -> log.severe("Failed to produce: " + e.getCause())
    ).forEach(recordMetadata -> {
      log.info("Send to new topic: " + recordMetadata.topic() + " - " + recordMetadata.offset());
    });
  }
}
