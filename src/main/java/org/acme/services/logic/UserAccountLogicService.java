package org.acme.services.logic;

import org.acme.domain.exceptions.RequiredPropertiesMissingException;
import org.acme.domain.exceptions.UserAlreadyExistsException;
import org.acme.domain.exceptions.UserNotFoundException;
import org.acme.domain.exceptions.WrongPasswordException;
import org.acme.domain.models.UserAccount;
import org.acme.domain.requests.UserAccountUpdateRequest;

public interface UserAccountLogicService {
    void registerUserAccount(UserAccount user) throws UserAlreadyExistsException, RequiredPropertiesMissingException;
    String login(String email, String password) throws UserNotFoundException, WrongPasswordException;

    String updateUserAccount(String email, UserAccountUpdateRequest updateRequest) throws WrongPasswordException, UserNotFoundException;

    void deleteUserAccount(UserAccount user);
    void logout(String email);
}
