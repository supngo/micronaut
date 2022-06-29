package com.naturecode.websocket;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.ClientWebSocket;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.reactivex.Single;

@ClientWebSocket("/ws/prices")
public abstract class PriceConsume implements AutoCloseable {
  private final Collection<String> observedMessages = new ConcurrentLinkedQueue<>();
  private WebSocketSession session;

  @OnOpen
  public void onOpen(WebSocketSession session) {
    this.session = session;
  }

  @OnMessage
  public void OnMessage(String message) {
    observedMessages.add(message);
  }

  public abstract void send(String message);

  public abstract Single<String> sendReactive(String message);

  public Collection<String> getObservedMessages() {
    return observedMessages;
  }

  public WebSocketSession getSession() {
    return session;
  }
}
