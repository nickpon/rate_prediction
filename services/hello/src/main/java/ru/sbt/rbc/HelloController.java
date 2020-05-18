package ru.sbt.rbc;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

//import io.prometheus.client.spring.web.PrometheusTimeMethod;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HelloController {
    @RequestMapping(path="/", method = GET)
//    @PrometheusTimeMethod(name = "hello_controller_say_hello_duration_seconds", help = "Some helpful info here")
    public String index() {
        return "Hello, user! This seems to be initial page!";
    }
}
