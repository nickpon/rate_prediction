package ru.sbt.prediction;

import java.util.AbstractMap;
import java.util.ArrayList;

class ResponseParser {
    static ArrayList<AbstractMap.SimpleEntry<Double, Long>> parseString(String response) {
        String[] splittedString = response.split(",");
        ArrayList<AbstractMap.SimpleEntry<Double, Long>> mapList = new ArrayList<>();
        for (String item : splittedString) {
            String[] parts = item.split("=");
            AbstractMap.SimpleEntry<Double, Long> params = new AbstractMap.SimpleEntry <> (
                    Double.parseDouble(parts[0]), Long.parseLong(parts[1])
            );
            mapList.add(params);
        }
        return mapList;
    }
}
