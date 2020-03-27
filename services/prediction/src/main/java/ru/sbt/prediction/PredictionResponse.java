package ru.sbt.prediction;

import static ru.sbt.prediction.ResponseParser.parseString;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@SpringBootApplication
@Component
public class PredictionResponse {
    private static String rbcURL;
    private static String weatherURL;
    private ArrayList<Double> tempList;
    private ArrayList<Double> rateList;

    @Autowired
    public PredictionResponse() {
        this.tempList = new ArrayList<>(30);
        this.rateList = new ArrayList<>(30);
    }

    public static void main(String[] args) {
        rbcURL = args[0];
        weatherURL = args[1];
        SpringApplication.run(PredictionResponse.class, args);
    }

    private void getRatesTemperature(
            ArrayList<AbstractMap.SimpleEntry<Double, Long>> ratesDates,
            ArrayList<AbstractMap.SimpleEntry<Double, Long>> temperatureDates
    ) {
        for (AbstractMap.SimpleEntry<Double, Long> rateDate : ratesDates) {
            rateList.add(rateDate.getKey());
            Long time = rateDate.getValue();
            for (AbstractMap.SimpleEntry<Double, Long> tempDate: temperatureDates) {
                if (time.equals(tempDate.getValue())) {
                    tempList.add(tempDate.getKey());
                }
            }
        }
    }

    private ArrayList<AbstractMap.SimpleEntry<Double, Long>> getResponseByURL(String url, RestTemplate restTemplate) {
        ResponseEntity<String> responseRBC = restTemplate.getForEntity(url, String.class);
        assert (responseRBC.getStatusCode().equals(HttpStatus.OK));
        return parseString(Objects.requireNonNull(responseRBC.getBody()));
    }

    @SuppressWarnings("WrapperTypeMayBePrimitive")
    Double predict(Double temperature) {
        RestTemplate restTemplate = new RestTemplate();

        ArrayList<AbstractMap.SimpleEntry<Double, Long>> ratesDates = getResponseByURL(
                rbcURL, restTemplate
        );

        ArrayList<AbstractMap.SimpleEntry<Double, Long>> temperatureDates = getResponseByURL(
                weatherURL, restTemplate
        );

        getRatesTemperature(ratesDates, temperatureDates);
        Double coefB = findB(tempList, rateList);
        Double coefA = findA(tempList, rateList, coefB);
        Double answer = coefA + temperature * coefB;

        return answer > 0 ? answer : 0.0;
    }

    @SuppressWarnings("WrapperTypeMayBePrimitive")
    private Double getMean(ArrayList<Double> arr) {
        Double sumArr = 0.;
        Double elementCount = 0.;
        for (Double elem : arr) {
            sumArr += elem;
            elementCount++;
        }
        return (Math.abs(elementCount - 0.00000001) < 0)? 99999.0: sumArr/(Math.abs(elementCount)+0.00000001);
    }

    @SuppressWarnings("WrapperTypeMayBePrimitive")
    private Double findB(ArrayList<Double> tempList, ArrayList<Double> rateList) {
        Double up = 0.;
        Double down = 0.;
        for (int i = 0; i < tempList.size(); i++) {
            up += (tempList.get(i) - getMean(tempList)) * (rateList.get(i) - getMean(rateList));
            down += Math.pow(tempList.get(i) - getMean(tempList), 2);
        }
        return (Math.abs(down - 0.00000001) < 0)? 99999.0: up / (Math.abs(down)+0.00000001);
    }

    private Double findA(ArrayList<Double> tempList, ArrayList<Double> rateList, Double b) {
        return getMean(rateList) - b * getMean(tempList);
    }
}