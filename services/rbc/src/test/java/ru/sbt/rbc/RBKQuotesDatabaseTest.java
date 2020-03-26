package ru.sbt.rbc;

import ru.sbt.rbc.DAO.Entity.Quote;
import ru.sbt.rbc.DAO.Repository.QuoteCrudRepository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes=RBCResponse.class)
@SpringBootTest
public class RBKQuotesDatabaseTest {
    @InjectMocks
    private RBCResponse responseRBC;

    @Mock
    private QuoteCrudRepository quoteCrudRepository;

    @Test
    public void norm() {
        Quote q = new Quote(12.0, Calendar.getInstance().getTime());
        Optional<Quote> opt = Optional.of(q);
        when(quoteCrudRepository.findByDate(anyString())).thenReturn(opt);
        assertEquals(Optional.ofNullable(responseRBC.getMaxQuoteMonth()), Optional.of(12.0));
    }
}
