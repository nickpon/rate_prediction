package ru.sbt.weather;

import ru.sbt.weather.presentations.Example;
import static ru.sbt.weather.Times.getUNIXTimes;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@SpringBootApplication
@Component
public class WeatherDollarResponse {
    private static final String key = "2029e1eb6e98f968af6c9fc1787eceb1";
    private static final String latitude = "55.0000";
    private static final String longitude = "37.5000";

    private RestTemplate restTemplate;

    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(WeatherDollarResponse.class, args);
    }

    @Autowired
    public WeatherDollarResponse() {
        this.restTemplate = restTemplate();
    }

    Double getTemperature(Long time) throws IOException {
        String url = "https://api.darksky.net/forecast/" + key + "/" + latitude + "," +
                longitude + "," + time + "?units=auto&exclude=currently,hourly,flags";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assert (response.getStatusCode().equals(HttpStatus.OK));
        Example example = new ObjectMapper().readValue(response.getBody(), Example.class);
        return example.getDaily().getData().get(0).getTemperatureHigh();
    }

    String array2String(ArrayList<AbstractMap.SimpleEntry<Double, Long>> response) {
        StringBuilder answer = new StringBuilder();
        for (AbstractMap.SimpleEntry<Double, Long> item : response) {
            answer.append(item.getKey().toString());
            answer.append("=");
            answer.append(item.getValue().toString());
            answer.append(",");
        }
        String output = answer.toString();
        return output.substring(0, output.length() - 1);
    }

    ArrayList<AbstractMap.SimpleEntry<Double, Long>> getArray(int period) throws IOException {
        ArrayList<AbstractMap.SimpleEntry<Double, Long>> array = new ArrayList<>(period);
        ArrayList<Long> times = getUNIXTimes(period);
        for (Long time : times) {
            AbstractMap.SimpleEntry<Double, Long> params = new AbstractMap.SimpleEntry <> (
                    getTemperature(time), time
            );
            array.add(params);
        }
        return array;
    }
}