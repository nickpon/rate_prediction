package ru.sbt.prediction.presentations;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    private Double value;
}