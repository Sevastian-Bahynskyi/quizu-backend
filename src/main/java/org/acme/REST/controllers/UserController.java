package org.acme.REST.controllers;


import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.acme.domain.UserAccount;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

@Path("/user")
public class UserController {
    @POST
    public RestResponse<String> registerUser(UserAccount user) {
        return ResponseBuilder
                .ok("JWT token") // TODO: implement real JWT token
                .build();
    }
}
