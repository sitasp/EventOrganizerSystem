package com.thinkify.events.model.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
