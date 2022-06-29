package in.silentsudo.micronaut.auth.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
// import lombok.extern.java.Log;

import java.security.Principal;

// @Log
@Controller("/secured")
public class SecuredController {

    @Get
    @Produces(value = MediaType.TEXT_PLAIN)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public String get(Principal principal) {
        // log.info("user {} accessed controller {}", principal.getName(), log.getName());
        return "Secured controller secured access";
    }

    @Get(uri = "/anonymous")
    @Produces(value = MediaType.TEXT_PLAIN)
    @Secured(SecurityRule.IS_ANONYMOUS)
    public String getAnonymous() {
        return "Secured controller anonymous access";
    }
}
