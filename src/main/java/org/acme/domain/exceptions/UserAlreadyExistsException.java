package org.acme.domain.exceptions;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(){
        super("User already exists");
    }

    public UserAlreadyExistsException(String message){
        super(message);
    }
}
