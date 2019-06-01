package com.example.SSO_SingleSignOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {


    @ExceptionHandler(CustomExaception.class)
    public String exception(CustomExaception customExaception) {
        return "customerror";
    }

}
