package org.acme.REST.controllers;


import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.acme.domain.UserAccount;
import org.acme.services.persistence.UserAccountPersistenceService;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

@Path("/user")
public class UserController {
    @Inject
    UserAccountPersistenceService persistenceService;

    @POST
    public RestResponse<String> registerUser(UserAccount user) {
        persistenceService.createUserAccount(user);

        return ResponseBuilder
                .ok("JWT token") // TODO: implement real JWT token
                .build();
    }
}
