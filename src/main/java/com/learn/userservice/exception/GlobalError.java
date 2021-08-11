package com.learn.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;
@Getter
public class GlobalError {


    private Integer errorCode;
    private HttpStatus httpStatus;
    private Date timestamp;
    private String url;
    private String exceptionClass;
    private String errorMessage;

    public GlobalError(String errorMessage, String exceptionClass, Date timestamp, String url, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.exceptionClass = exceptionClass;
        this.timestamp = timestamp;
        this.url = url;
        this.httpStatus = httpStatus;
        this.errorCode = httpStatus.value();
    }

}
