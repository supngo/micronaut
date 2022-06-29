package com.naturecode.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.naturecode.model.Symbol;
import com.naturecode.persistence.SymbolEntity;
import com.naturecode.persistence.SymbolRepo;
import com.naturecode.utils.InMemStore;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;

@Controller("/symbols")
public class SymbolController {
  
  private final InMemStore inMemStore;

  private final SymbolRepo symbolRepo;

  public SymbolController(InMemStore inMemStore, SymbolRepo symbolRepo) {
    this.inMemStore = inMemStore;
    this.symbolRepo = symbolRepo;
  }

  @Secured(SecurityRule.IS_AUTHENTICATED)
  @Get
  public List<Symbol> getAll(){
    return new ArrayList<>(inMemStore.getSymbols().values());
  }

  // Path parameter
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Get("{value}")
  public Symbol getSymbolByValue(@PathVariable String value) {
    return inMemStore.getSymbols().get(value);
  }

  // Query parameter
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Get("/filter{?max,offset}") 
  public List<Symbol> getSymbols(@QueryValue Optional<Integer> max, @QueryValue Optional<Integer> offset) {
    return inMemStore.getSymbols().values()
    .stream().skip(offset.orElse(0))
    .limit(max.orElse(1))
    .toList();
  }

  @Secured(SecurityRule.IS_ANONYMOUS)
  @Get("/jpa")
  public Single<List<SymbolEntity>> allSymbols() {
    return Single.just(symbolRepo.findAll());
  }
}
