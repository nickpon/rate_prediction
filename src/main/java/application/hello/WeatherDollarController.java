package application.hello;

import application.hello.presentation.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class WeatherDollarController {
    private WeatherDollarResponse responser;

    @Autowired
    public WeatherDollarController(WeatherDollarResponse responser) {
        this.responser = responser;
    }

    @RequestMapping(
        value = "/weather",
        params = {"temperature"},
        method = POST
    )
//    @ResponseBody
    public Response index(@RequestParam("temperature") Double temperature) throws IOException, ParseException {
        Response response = new Response();
        response.setValue(responser.predict(temperature));
        return response;
    }
}