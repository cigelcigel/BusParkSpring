package com.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        return "errorSpring";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
