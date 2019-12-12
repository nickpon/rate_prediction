package application.hello;

import application.hello.presentation.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class KekController {
    private RBKResponse keker;

    @Autowired
    public KekController(RBKResponse keker) {
        this.keker = keker;
    }

    @RequestMapping(path="/request", method = GET)
    public Response index() {
        Response response = new Response();
        response.setValue(keker.getMaxQuoteMonth());
        return response;
    }
}