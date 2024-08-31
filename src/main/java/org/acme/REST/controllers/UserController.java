package org.acme.REST.controllers;


import jakarta.inject.Inject;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.acme.domain.requests.LoginRequest;
import org.acme.domain.models.UserAccount;
import org.acme.domain.requests.UserAccountUpdateRequest;
import org.acme.services.logic.UserAccountLogicService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

@Path("/user")
public class UserController {
    @Inject
    UserAccountLogicService logicService;
    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/register")
    public RestResponse<String> registerUser(UserAccount user) {

        try {
            logicService.registerUserAccount(user);
        } catch (Exception e) {
            return ResponseBuilder
                    .create(RestResponse.Status.BAD_REQUEST, e.getMessage())
                    .build();
        }

        return ResponseBuilder
                .ok("User is registered.") // TODO: implement real JWT token
                .build();
    }

    @PATCH
    public RestResponse<String> updateUser(UserAccountUpdateRequest userAccountUpdateRequest) {
        // TODO: update user only when logged and retrieve current email from the JWT
        try {
//            logicService.updateUserAccount(userAccountUpdateRequest);
        } catch (Exception e) {
            return ResponseBuilder
                    .create(RestResponse.Status.BAD_REQUEST, e.getMessage())
                    .build();
        }

        return ResponseBuilder
                .ok("The account info was updated.") // TODO: implement real JWT token
                .build();
    }

    @POST
    @Path("/login")
    public RestResponse<String> login(LoginRequest userLoginData) {
        try {
            String token = logicService.login(userLoginData.getEmail(), userLoginData.getPassword());

            return ResponseBuilder
                    .ok(token)
                    .build();
        } catch (Exception e) {
            return ResponseBuilder
                    .create(RestResponse.Status.UNAUTHORIZED, e.getLocalizedMessage())
                    .build();
        }
    }
}
