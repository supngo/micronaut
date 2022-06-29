package com.naturecode;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "producer")
public interface QuoteProducer {

  @Topic("quotes")
  void send(@KafkaKey String symbol, Quote quote);
}
