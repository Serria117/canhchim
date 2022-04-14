package com.canhchim.controllers.handlers;

import com.canhchim.payload.response.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileUploadHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseObject> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException e){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ResponseObject(406,"NOT_ACCEPT",
                        "One or more files are too large. The maximum allowed file size is 5 Megabyte."));
    }

}
