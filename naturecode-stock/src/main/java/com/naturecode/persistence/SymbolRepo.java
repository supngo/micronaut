package com.naturecode.persistence;

import java.util.List;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface SymbolRepo extends CrudRepository<SymbolEntity, Integer> {
  
  @Override
  List<SymbolEntity> findAll();
}
