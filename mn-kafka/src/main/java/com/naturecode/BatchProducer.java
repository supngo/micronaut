package com.naturecode;

import java.util.List;

import org.apache.kafka.clients.producer.RecordMetadata;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.reactivex.Flowable;

@KafkaClient(id = "batch_producer", batch = true)
public interface BatchProducer {

  @Topic("new_quotes")
  Flowable<RecordMetadata> send(List<Quote> new_quote);
}
