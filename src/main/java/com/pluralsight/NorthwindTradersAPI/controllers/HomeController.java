package com.pluralsight.NorthwindTradersAPI.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping(path = "/howdy", method = RequestMethod.GET)
    public String greetIndex(
            @RequestParam(name = "greetName", defaultValue = "Pardner") String name
    ) {
        return "Howdy, " + name + "!";
    }

    @RequestMapping(path = "/buhbye", method = RequestMethod.GET)
    public String byeIndex(
            @RequestParam(name = "departName", defaultValue = "Pardner") String name
    ) {
        return "Y'all come back now, ya hear, " + name + "?";
    }
}