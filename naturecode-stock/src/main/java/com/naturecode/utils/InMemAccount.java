package com.naturecode.utils;

import java.util.HashMap;
import java.util.UUID;

import com.naturecode.model.WatchList;

import jakarta.inject.Singleton;

@Singleton
public class InMemAccount {
  private final HashMap<UUID, WatchList> watchList = new HashMap<>();

  public WatchList get(final UUID id) {
    return watchList.getOrDefault(id, new WatchList());
  }

  public WatchList update(final UUID id, final WatchList watchlist) {
    watchList.put(id, watchlist);
    return get(id);
  }

  public void delete(final UUID id) {
    watchList.remove(id);
  }
}
