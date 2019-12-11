package application.hello;

import application.hello.DAO.Entity.Quote;
import application.hello.DAO.Repository.QuoteCrudRepository;
import com.sun.org.apache.xpath.internal.operations.Quo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RBKQuotesDatabaseTest {

    @InjectMocks
    private RBKResponse responser = new RBKResponse(new RestTemplate());

    @Mock
    private QuoteCrudRepository quoteCrudRepository;

    @Test
    public void norm() {
        Quote q = new Quote(12.0, Calendar.getInstance().getTime());
        Optional<Quote> opt = Optional.of(q);
        when(quoteCrudRepository.findByDate(anyString())).thenReturn(opt);
        assertEquals(Optional.ofNullable(responser.getMaxQuoteMonth()), Optional.ofNullable(12.0));
    }
}
