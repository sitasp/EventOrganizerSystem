package com.thinkify.events.exception;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String message){
        super(message);
    }
}
