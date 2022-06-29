package com.naturecode.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.naturecode.persistence.QuoteDTO;
import com.naturecode.persistence.QuoteEntity;
import com.naturecode.persistence.QuoteRepo;
import com.naturecode.utils.InMemStore;

import io.micronaut.data.model.Pageable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;

@Controller("/quotes")
public class QuoteController {
  // private final InMemStore inMemStore;

  private final QuoteRepo quoteRepo;

  public QuoteController(QuoteRepo quoteRepo) {
    // this.inMemStore = inMemStore;
    this.quoteRepo = quoteRepo;
  }
 
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Get("/jpa")
  public Single<List<QuoteEntity>> allQuotes() {
    return Single.just(quoteRepo.findAll());
  }

  @Secured(SecurityRule.IS_ANONYMOUS)
  @Get("/jpa/order/desc")
  public List<QuoteDTO> orderedQuotesDesc() {
    return quoteRepo.listOrderByVolumeDesc();
  }

  @Secured(SecurityRule.IS_ANONYMOUS)
  @Get("/jpa/order/asc")
  public List<QuoteDTO> orderedQuotesAsc() {
    return quoteRepo.listOrderByVolumeAsc();
  }

  @Secured(SecurityRule.IS_ANONYMOUS)
  @Get("/jpa/volume/{volume}")
  public List<QuoteDTO> volumeFilter(@PathVariable BigDecimal volume) {
    return quoteRepo.findByVolumeGreaterThan(volume);
  }

  @Secured(SecurityRule.IS_ANONYMOUS)
  @Get("/jpa/page/{?page, volume}")
  public List<QuoteDTO> volumeFilterPage(
    @QueryValue Optional<Integer> page,
    @QueryValue Optional<BigDecimal> volume) {
    return quoteRepo.findByVolumeGreaterThan(
      volume.isEmpty() ? BigDecimal.valueOf(0) : volume.get(),
      Pageable.from(page.isEmpty() ? 0 : page.get()));
  }
}
