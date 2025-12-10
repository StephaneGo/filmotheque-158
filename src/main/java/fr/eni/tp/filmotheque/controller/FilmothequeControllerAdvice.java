package fr.eni.tp.filmotheque.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class FilmothequeControllerAdvice {

    @ExceptionHandler(value=RuntimeException.class)
    public String handleError(HttpServletRequest req, Exception e) throws Exception {
        return "view-error";
    }

}
