package ru.sbt.prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sbt.prediction.presentations.Response;

@RestController
public class PredictionController {
    private PredictionResponse responser;

    @Autowired
    public PredictionController(PredictionResponse responser) {
        this.responser = responser;
    }

    @RequestMapping(
            value = "/prediction",
            params = {"temperature"}
    )
    public Response request(@RequestParam("temperature") Double temperature) {
        Response response = new Response();
        response.setValue(responser.predict(temperature));
        return response;
    }
}
