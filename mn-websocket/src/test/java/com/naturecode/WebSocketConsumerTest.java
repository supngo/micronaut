package com.naturecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.naturecode.websocket.PriceConsume;

import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava2.http.client.websockets.RxWebSocketClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
public class WebSocketConsumerTest {
  @Inject
  @Client("http://localhost:9090")
  RxWebSocketClient client;

  PriceConsume priceConsumer;

  @BeforeEach
  void connect() {
    priceConsumer = client.connect(PriceConsume.class, "/ws/prices").blockingFirst();
  }

  @Test
  void SyncConsumerTest() {
    var testString = "Hello";
    priceConsumer.send(testString);
    Awaitility.await().timeout(Duration.ofSeconds(9)).untilAsserted(() -> {
      final Object[] messages = priceConsumer.getObservedMessages().toArray();
      assertEquals("Connected!", messages[0]);
      assertEquals("Not supported: " + testString, messages[1]);
    });
  }

  @Test
  void AsyncConsumerTest() {
    var testString = "Hello";
    priceConsumer.sendReactive(testString).blockingGet();
    Awaitility.await().timeout(Duration.ofSeconds(9)).untilAsserted(() -> {
      final Object[] messages = priceConsumer.getObservedMessages().toArray();
      assertEquals("Connected!", messages[0]);
      assertEquals("Not supported: " + testString, messages[1]);
    });
  }
}
