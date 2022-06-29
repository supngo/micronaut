package com.naturecode;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.databind.JsonNode;
import com.naturecode.model.Symbol;
import com.naturecode.utils.InMemStore;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;

import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@MicronautTest
class NaturecodeStockTest {
  @Inject
  @Client("/")
  HttpClient client;

  @Inject
  InMemStore inMemStore;

  @BeforeEach
  void Setup() {
    inMemStore.initializeWith(10);
  }

  @Test
  void symbolTest() throws ParseException {
    UsernamePasswordCredentials creds = new UsernamePasswordCredentials("naturecode", "secret");
    HttpRequest<?> request = HttpRequest.POST("/login", creds); // <4>
    HttpResponse<BearerAccessRefreshToken> rsp = client.toBlocking().exchange(request, BearerAccessRefreshToken.class); // <5>
    BearerAccessRefreshToken bearerAccessRefreshToken = rsp.body();

    assertEquals(HttpStatus.OK, rsp.getStatus());
    assertNotNull(bearerAccessRefreshToken.getAccessToken());
    assertTrue(JWTParser.parse(bearerAccessRefreshToken.getAccessToken()) instanceof SignedJWT);

    String accessToken = bearerAccessRefreshToken.getAccessToken();
    HttpRequest<?> requestWithAuthorization = HttpRequest.GET("/symbols")
            .accept(MediaType.APPLICATION_JSON)
            .bearerAuth(accessToken);
    var response = client.toBlocking().exchange(requestWithAuthorization, JsonNode.class);

    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals(10, response.getBody().get().size());
  }

  @Test
  void symbolWithValueTest() {
    var testSymbol = new Symbol("TEST");
    inMemStore.getSymbols().put(testSymbol.value(), testSymbol);
    var response = client.toBlocking().exchange("symbols/" + testSymbol.value(), Symbol.class);
    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals(testSymbol, response.getBody().get());
  }

  @Test
  void symbolWithQueryTest() {
    var response = client.toBlocking().exchange("symbols/filter?max=5", JsonNode.class);
    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals(5, response.getBody().get().size());
  }
}
