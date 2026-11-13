package com.ariga.users_api.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long Id){
        super("User with Id "+ Id + " was not found");
    }
    
}
