package com.naturecode.controller;

import java.util.UUID;

import com.naturecode.error.CustomException;
import com.naturecode.model.RestApiResponse;
import com.naturecode.model.WatchList;
import com.naturecode.utils.InMemAccount;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.extern.java.Log;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/account/watch")
@Log
public record AccountController(InMemAccount store) {
  static final UUID ID = UUID.randomUUID();

  @Get
  public WatchList get() {
    return store.get(ID);
  }

  @Put(
    consumes = MediaType.APPLICATION_JSON,
    produces = MediaType.APPLICATION_JSON
  )
  public HttpResponse<RestApiResponse> update(@Body WatchList watchList) {
    if(watchList.getSymbols().isEmpty()) {
      log.severe("WatchList is empty!");
      throw new CustomException(String.format("Please pass a non empty WatchList"));
      // return HttpResponse.badRequest().body( new CustomError(
      //   HttpStatus.BAD_REQUEST.getCode(),
      //   "WatchList is empty",
      //   String.format("Please pass a non empty WatchList")
      // ));
    }
    return HttpResponse.ok().body(store.update(ID, watchList));
  }

  // @Status(HttpStatus.CREATED)
  @Delete(
    produces = MediaType.APPLICATION_JSON
  )
  public HttpResponse<Void> delete() {
    store.delete(ID);
    return HttpResponse.noContent();
  }
}
