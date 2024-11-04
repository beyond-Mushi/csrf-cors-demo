package org.example.csrfcorsimplementationdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final Logger logger = Logger.getLogger(HomeController.class.getName());

    @GetMapping("/")
    public String home() {
        logger.info("Calling :::::: home");
        return "index";
    }

    @PostMapping("/test")
    @ResponseBody
    public String test() {
        logger.info("Calling :::::: Test" );
        return "Hello Test";
    }
}
