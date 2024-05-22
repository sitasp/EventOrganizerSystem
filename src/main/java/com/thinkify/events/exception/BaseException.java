package com.thinkify.events.exception;

import java.io.Serial;

public class BaseException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public BaseException(){
        super();
    }

    public BaseException(String message){
        super(message);
    }

    public BaseException(String message, Throwable throwable){
        super(message, throwable);
    }
}
