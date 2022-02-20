package com.canhchim.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseObject {
    int responseCode;
    String message;
    Object data;
}
