package com.thinkify.events.model.response;


import lombok.Data;

@Data
public class BaseResponse {
    int statusCode;
    int errorCode;
    String message;
}
