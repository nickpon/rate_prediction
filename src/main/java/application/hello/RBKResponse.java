package application.hello;

import application.hello.DAO.Entity.Quote;
import application.hello.DAO.Repository.QuoteCrudRepository;
//import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class RBKResponse {

    private RestTemplate restTemplate;
    @Autowired
    private QuoteCrudRepository quoteCrudRepository;

    @Autowired
    public RBKResponse(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String[] getResponse() {
        String url = "http://export.rbc.ru/free/selt.0/free.fcgi?period=DAILY&tickers=USD000000TOD&separator=,&data_format=BROWSER&lastdays=";
        ResponseEntity<String> response = restTemplate.getForEntity(url + "30", String.class);
        assert (response.getStatusCode().equals(HttpStatus.OK));
        return Objects.requireNonNull(response.getBody()).split("\n");
    }

    public Double getMax(String[] lines) {
        Double maxRate = 0.0;
        for (String line1 : lines) {
            String[] line = line1.split(",");
            maxRate = Math.max(maxRate, Double.parseDouble(line[line.length - 1]));
        }
        return maxRate;
    }

    public ArrayList<AbstractMap.SimpleEntry<Double, Long>> getValues() throws ParseException {
        ArrayList<AbstractMap.SimpleEntry<Double, Long>> values = new ArrayList<>(30);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5:00"));
        for (String line1 : getResponse()) {
            String[] line = line1.split(",");
            Date tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(line[1]);
            cal.setTime(tempDate);
            AbstractMap.SimpleEntry<Double, Long> params = new AbstractMap.SimpleEntry <> (Double.parseDouble(line[line.length - 1]), cal.getTimeInMillis() / 1000L);
            values.add(params);
        }
        return values;
    }

    @Transactional
    public void saveQuote(Double quote) {
        Date date = Calendar.getInstance().getTime();
        quoteCrudRepository.save(new Quote(quote, date));
    }

    @Transactional
    public Optional<Double> getQuoteByDate(Date date) {
        String dateString = Quote.dateFormat(date);
        Optional<Quote> rate = quoteCrudRepository.findByDate(dateString);
        return rate.map(Quote::getQuote);
    }

    public Double getMaxQuoteMonth() {
        Optional<Double> maxQuote = getQuoteByDate(Calendar.getInstance().getTime());
        if (maxQuote.isPresent()) {
            System.out.println("Database");
            return maxQuote.get();
        }
        System.out.println("Server");
        Double quote = getMax(getResponse());
        saveQuote(quote);
        return quote;
    }
}
