package ru.sbt.weather;

import ru.sbt.weather.presentations.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class WeatherDollarController {
    private WeatherDollarResponse response;

    @Autowired
    public WeatherDollarController(WeatherDollarResponse response) {
        this.response = response;
    }

    @RequestMapping(
            value = "/weather",
            params = {"time"},
            method = GET
    )
    public Response request(@RequestParam("time") Long time) throws IOException {
        Response response = new Response();
        response.setValue(this.response.getTemperature(time));
        return response;
    }


    @RequestMapping(
            value = "/weather/array"
    )
    public String requestArray() throws IOException {
        return response.array2String(response.getArray(50));
    }
}