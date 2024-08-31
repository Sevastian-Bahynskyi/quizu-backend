package org.acme.services.logic.impl;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.models.UserAccount;
import org.acme.domain.requests.UserAccountUpdateRequest;
import org.acme.services.logic.UserAccountLogicService;
import org.acme.services.persistence.UserAccountPersistenceService;
import org.acme.services.security.UserAccountSecurityService;
import org.jboss.resteasy.reactive.common.NotImplementedYet;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class UserAccountAccountLogicServiceImpl implements UserAccountLogicService {
    @Inject
    UserAccountPersistenceService persistenceService;

    @Inject
    UserAccountSecurityService securityService;

    // TODO: look into the appropriate exceptions
    @Override
    public void registerUserAccount(UserAccount user) throws Exception {
        if (persistenceService.getUserAccount(user.getEmail()) != null) {
            throw new Exception("User account already exists");
        }

        try {
            user.setPassword(securityService.hashPassword(user.getPassword()));
        } catch (Exception e) {
            throw new SecurityException("Something went wrong, probably the password is incorrect.");
        }
        persistenceService.createUserAccount(user); // TODO: add error handling
    }

    @Override
    public String login(String email, String password) throws Exception {
        String hashedPassword = persistenceService.getUserAccount(email).getPassword();

        if (!securityService.passwordMatches(password, hashedPassword)) {
            throw new SecurityException("The entered password is incorrect.");
        }

        Set<String> groups = new HashSet<>();
        groups.add("User");
        groups.add("Quiz Owner");

        return Jwt
                .upn("author")
                .groups(groups)
                .expiresAt(System.currentTimeMillis() + 3600 * 7) // 1 hour expiration
                .sign();
    }

    @Override
    public String updateUserAccount(String email, UserAccountUpdateRequest updateRequest) throws Exception {
        UserAccount account = persistenceService.getUserAccount(email);
        String hashedPassword = persistenceService.getUserAccount(email).getPassword();
        UserAccount updatedUser = new UserAccount();


        if (account.getEmail() != null) {
            account.setEmail(updateRequest.getEmail());
            updatedUser.setEmail(account.getEmail());
        }

        if (updateRequest.getOldPassword() != null && updateRequest.getNewPassword() != null) {
            if (!securityService.passwordMatches(updateRequest.getOldPassword(), hashedPassword)) {
                throw new SecurityException("The entered password is incorrect.");
            }

            account.setPassword(updateRequest.getNewPassword());
            updatedUser.setPassword(account.getPassword());
        }

        if(updateRequest.getUsername() != null) {
            account.setUsername(updateRequest.getUsername());
            updatedUser.setUsername(account.getUsername());
        }


        persistenceService.updateUserAccount(account);

        return issueToken(updatedUser);
    }

    @Override
    public void deleteUserAccount(UserAccount user) {
        throw new NotImplementedYet();
    }

    @Override
    public void logout(String email) {
        throw new NotImplementedYet();
    }

    private String issueToken(UserAccount user)
    {
        return Jwt.issuer("default-issuer")
                .upn(user.getEmail())
                .groups(new HashSet<>(Set.of("User", "Quiz Owner")))
                .expiresIn(Duration.ofDays(2))
                .claim("username", user.getUsername())
                .sign();
    }
}
