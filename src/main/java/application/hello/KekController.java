package application.hello;

import application.hello.presentation.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KekController {
    private RBKResponse keker;

    @Autowired
    public KekController(RBKResponse keker) {
        this.keker = keker;
    }

    @RequestMapping("/request")
    public Response index() {
        Response response = new Response();
        response.value = keker.getMaxQuoteMonth();
        return response;
    }
}