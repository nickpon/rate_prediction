package ru.sbt.rbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
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
    private RBCResponse responseRBC;

    @Test
    public void getMax() {
        assertEquals(responseRBC.getMax(HALLOS), 24.53, 0.0001);
    }

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ResponseEntity<String> response;

    @Test
    public void testKeker2() {
        when(response.getStatusCode()).thenReturn(HttpStatus.OK);
        when(response.getBody()).thenReturn("Hallo,2.28");
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(response);
    }
}