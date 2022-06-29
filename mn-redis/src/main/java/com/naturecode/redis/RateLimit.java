package com.naturecode.redis;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@Controller("/time")
public class RateLimit {
  private static final Logger log = LoggerFactory.getLogger(RateLimit.class);
  private StatefulRedisConnection<String, String> redis;
  private final static int QUOTE_PER_MINUTE = 10;

  public RateLimit(final StatefulRedisConnection<String, String> redis) {
    this.redis = redis;
  }

  @ExecuteOn(TaskExecutors.IO)
  @Get("/")
  public String time() {
    final String key = "Example:Time";
    final String value = redis.sync().get(key);
    int currentQuota = null == value ? 0 : Integer.parseInt(value);
    
    if(currentQuota >= QUOTE_PER_MINUTE) {
      final String error = String.format("Rate limite reached %s %s/%s", key, currentQuota, QUOTE_PER_MINUTE);
      log.info(error);
      return error;
    }
    log.info("Current quota {} in {}/{}", key, currentQuota, QUOTE_PER_MINUTE);
    increaseQuota(key);
    return LocalTime.now().toString();
  }

  private void increaseQuota(final String key) {
    final RedisCommands<String, String> commands = redis.sync();
    commands.multi();
    commands.incrby(key, 1);
    var remainingSeconds = 60 - LocalTime.now().getSecond();
    commands.expire(key, remainingSeconds);
    commands.exec();
  }
}
