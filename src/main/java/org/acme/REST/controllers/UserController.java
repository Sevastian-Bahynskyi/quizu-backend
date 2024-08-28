package org.acme.REST.controllers;


import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.acme.domain.LoginRequest;
import org.acme.domain.UserAccount;
import org.acme.services.persistence.UserAccountPersistenceService;
import org.acme.services.security.UserSecurityService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

@Path("/user")
public class UserController {
    @Inject
    UserAccountPersistenceService persistenceService;
    @Inject
    JsonWebToken jwt;
    @Inject
    UserSecurityService securityService;

    @POST
    @Path("/register")
    public RestResponse<String> registerUser(UserAccount user) throws Exception {
        // if there is user with such email, don' register
        user.setPassword(securityService.hashPassword(user.getPassword()));
        persistenceService.createUserAccount(user);
        return ResponseBuilder
                .ok("User is registered.") // TODO: implement real JWT token
                .build();
    }

    @POST
    @Path("/login")
    public RestResponse<String> login(LoginRequest userLoginData) throws Exception {
        // make sure all edges are handled
        // if there is no user with such email
        String hashedPassword = persistenceService.getUserAccount(userLoginData.getEmail()).getPassword();
        if (!securityService.passwordMatches(userLoginData.getPassword(), hashedPassword)) {
            return ResponseBuilder
                    .create(RestResponse.Status.UNAUTHORIZED, "Bad")
                    .build();
        }

        return ResponseBuilder
                .ok("JWT token") // issue JWT token
                .build();
    }

    // TODO: add 2-nd Tier (logic)
    // TODO: issue the JWT token when login
    // TODO: JWT should have roles and some claims
}
