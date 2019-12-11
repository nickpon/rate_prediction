package application.hello;

import application.hello.DAO.Entity.Quote;
import application.hello.DAO.Repository.QuoteCrudRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RBKDatabaseTest {

    @Autowired
    private QuoteCrudRepository quoteCrudRepository;

    @Test
    public void test() {
        Double quote = 42.0;
        Date date = Calendar.getInstance().getTime();
        quoteCrudRepository.save(new Quote(quote, date));
        Assert.assertTrue(quoteCrudRepository.findByDate(Quote.dateFormat(date)).map(Quote::
                getQuote).isPresent());
        Assert.assertEquals(quoteCrudRepository.findByDate(Quote.dateFormat(date)).map(Quote::
                getQuote).get(), quote);
    }
}
