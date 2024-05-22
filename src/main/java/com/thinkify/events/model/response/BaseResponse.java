package com.thinkify.events.model.response;


import lombok.Data;

@Data
public class BaseResponse {
    Integer statusCode;
    Integer errorCode;
    String message;

    public BaseResponse(String message){
        this.message = message;
    }

    public BaseResponse(){
    }
}
