package ru.sbt.rbc;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HelloController {
    @RequestMapping(path="/", method = GET)
    public String index() {
        return "Hello, user! This seems to be initial page!";
    }
}
