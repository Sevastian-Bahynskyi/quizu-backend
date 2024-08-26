package org.acme.services.persistence;

import org.acme.domain.UserAccount;

public interface UserAccountPersistenceService {
    UserAccount getUserAccount(String email);
    void createUserAccount(UserAccount userAccount);
    UserAccount updateUserAccount(UserAccount userAccount);
    void deleteUserAccount(String email);

    UserAccount updateUserAccountEmail(String oldEmail, String newEmail); // TODO: implement
}
