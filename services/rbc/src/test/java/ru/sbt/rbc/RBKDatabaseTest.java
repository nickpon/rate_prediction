package ru.sbt.rbc;

import ru.sbt.rbc.DAO.Entity.Quote;
import ru.sbt.rbc.DAO.Repository.QuoteCrudRepository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=RBCResponse.class)
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
