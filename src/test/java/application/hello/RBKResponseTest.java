package application.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RBKResponseTest {
    private static final String[] HALLOS = new String[]{
            "Hallo,2019-09-24,12.43",
            "Danke,2019-01-01,24.53",
            "Three,2019-05-06,24.52"
    };

    @InjectMocks
    private RBKResponse keker = new RBKResponse(new RestTemplate());

//    @Test
//    public void getMax() {
//        assertEquals(keker.getMax(HALLOS), "Answer: 24.53\n");
//    }

    @Mock
    private RestTemplate keker2;

    @Mock
    private ResponseEntity<String> lol;

    @Test
    public void testKeker2() {
        when(lol.getStatusCode()).thenReturn(HttpStatus.OK);
        when(lol.getBody()).thenReturn("Hallo,2.28");
        when(keker2.getForEntity(anyString(), eq(String.class))).thenReturn(lol);
    }
}