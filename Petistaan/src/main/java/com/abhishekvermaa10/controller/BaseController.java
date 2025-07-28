package com.abhishekvermaa10.controller;

import com.abhishekvermaa10.dto.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;


public abstract class BaseController {
    @Autowired
    protected MessageSource messageSource;

    protected <T> ResponseEntity<WebResponse<T>> success (T data, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(
                WebResponse.<T>builder()
                        .data(data)
                        .message(getMessage())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }


    private String getMessage (){
        return messageSource.getMessage("api.response.success", null, LocaleContextHolder.getLocale());
    }

}
