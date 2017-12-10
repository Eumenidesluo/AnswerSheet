package me.eumenides.controller;

import me.eumenides.utils.UnLoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Eumenides on 2017/11/30.
 */
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(UnLoginException.class)
    public String exceptionHandler(){
        return "redirect:loginPage";
    }
}
