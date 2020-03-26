package ru.sbt.rbc;

import ru.sbt.rbc.presentations.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class RBCController {
    private RBCResponse responseRBC;

    @Autowired
    public RBCController(RBCResponse responseRBC) {
        this.responseRBC = responseRBC;
    }

    @RequestMapping(path="/rbc", method = GET)
    public Response request() {
        Response response = new Response();
        response.setValue(responseRBC.getMaxQuoteMonth());
        return response;
    }

    @RequestMapping(
            path="/rbc/array"
    )
    public String requestArray() throws ParseException {
        return responseRBC.array2String(responseRBC.getValues());
    }
}
