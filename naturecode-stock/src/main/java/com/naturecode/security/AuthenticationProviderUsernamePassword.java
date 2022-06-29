package com.naturecode.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.reactivestreams.Publisher;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import jakarta.inject.Singleton;

@Singleton
public class AuthenticationProviderUsernamePassword implements AuthenticationProvider {
  @Override
  public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest,
      final AuthenticationRequest<?, ?> authenticationRequest) {
    return Flowable.create(emitter -> {
      final Object identity = authenticationRequest.getIdentity();
      final Object secret = authenticationRequest.getSecret();
      if(identity.equals("naturecode") && secret.equals("secret")) {
        emitter.onNext(AuthenticationResponse.success((String) identity, new ArrayList<>(List.of("Admin", "User"))));
        // emitter.onNext(AuthenticationResponse.success("naturecode"));
        emitter.onComplete();
        return;
      }
      emitter.onError(new AuthenticationException(new AuthenticationFailed("Invalid username or password!")));
    }, BackpressureStrategy.ERROR);
  }  
}
