package org.acme.services.logic;

import org.acme.domain.models.UserAccount;
import org.acme.domain.requests.UserAccountUpdateRequest;

public interface UserAccountLogicService {
    void registerUserAccount(UserAccount user) throws Exception;
    String login(String email, String password) throws Exception;

    String updateUserAccount(String email, UserAccountUpdateRequest updateRequest) throws Exception;

    void deleteUserAccount(UserAccount user);
    void logout(String email);
}
