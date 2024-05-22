package com.thinkify.events.exception;

import com.thinkify.events.entity.Event;

public class EventNotFoundException extends BaseException {
    public EventNotFoundException(){
        super();
    }

    public EventNotFoundException(String message){
        super(message);
    }
}
