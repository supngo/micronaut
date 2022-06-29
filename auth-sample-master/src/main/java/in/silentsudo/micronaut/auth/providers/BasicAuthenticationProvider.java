package in.silentsudo.micronaut.auth.providers;

import io.micronaut.security.authentication.*;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class BasicAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Publisher<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        final String identity = (String) authenticationRequest.getIdentity();
        final String secret = (String) authenticationRequest.getSecret();
        // log.debug("Basic " + identity + " access");
        return identity.equals(secret) ? Flowable.just(new UserDetails(identity, Collections.emptyList()))
                : Flowable.just(new AuthenticationFailed());
    }
}
