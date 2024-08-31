package org.acme.services.persistence;

import org.acme.domain.models.UserAccount;

public interface UserAccountPersistenceService {
    UserAccount getUserAccount(String email) throws Exception;
    void createUserAccount(UserAccount userAccount);
    UserAccount updateUserAccount(UserAccount userAccount);
    void deleteUserAccount(String email);

    UserAccount updateUserAccountEmail(String oldEmail, String newEmail); // TODO: implement
}
