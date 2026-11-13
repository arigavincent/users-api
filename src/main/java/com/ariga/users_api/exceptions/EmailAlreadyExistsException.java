package com.ariga.users_api.exceptions;

public class EmailAlreadyExistsException  extends RuntimeException{
    public EmailAlreadyExistsException(String Email){
        super("User With Email " + Email + " Already Exists");
    }
    
}
