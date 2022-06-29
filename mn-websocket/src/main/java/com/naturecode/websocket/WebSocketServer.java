package com.naturecode.websocket;

import org.reactivestreams.Publisher;

import io.micronaut.websocket.CloseReason;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import io.reactivex.Flowable;
import lombok.extern.java.Log;

@ServerWebSocket("/ws/prices")
@Log
public class WebSocketServer {
  
  @OnOpen
  public Publisher<String> onOpen(WebSocketSession session) {
    return session.send("Connected!");
  }

  @OnMessage
  public Publisher<String> onMessage(String message, WebSocketSession session) {
    log.info("Received message: " + message + " from session: " + session.getId());
    if(message.contentEquals("disconnect")) {
      session.close(CloseReason.NORMAL);
      return Flowable.empty();
    }
    return session.send("Not supported: " + message);
  }

  @OnClose
  public void onClose(WebSocketSession session) {
    log.info("Session " + session.getId() + " closed!");
  }
}
