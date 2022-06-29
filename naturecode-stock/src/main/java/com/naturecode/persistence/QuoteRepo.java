package com.naturecode.persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface QuoteRepo extends CrudRepository<QuoteEntity, Integer> {
  
  @Override
  List<QuoteEntity> findAll();

  Optional<QuoteEntity> findBySymbol(SymbolEntity entity);

  // Ordering
  List<QuoteDTO> listOrderByVolumeDesc();
  List<QuoteDTO> listOrderByVolumeAsc();

  // Filter
  List<QuoteDTO> findByVolumeGreaterThan(BigDecimal volume);
  // GreaterThenEquals
  // LessThan
  // LessThanEquals
  // Like
  // InList
  // InRange
  // IsNull
  // IsNotNull
  // IsEmpty
  // IsNotEmpty

  // Pagination
  List<QuoteDTO> findByVolumeGreaterThan(BigDecimal volume, Pageable pageable);
  Slice<QuoteDTO> list(Pageable pageable);
}
