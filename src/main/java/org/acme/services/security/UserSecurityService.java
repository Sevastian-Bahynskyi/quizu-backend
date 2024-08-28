package org.acme.services.security;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.elytron.security.common.BcryptUtil;

@ApplicationScoped
public class UserSecurityService {
    public String hashPassword(String plainPassword) throws Exception {
        return BcryptUtil.bcryptHash(plainPassword);
    }

    public boolean passwordMatches(String plainPassword, String hashedPassword) throws Exception {
        return BcryptUtil.matches(plainPassword, hashedPassword);
    }
}
