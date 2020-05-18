package ru.sbt.rbc;

import ru.sbt.rbc.DAO.Entity.Quote;
import ru.sbt.rbc.DAO.Repository.QuoteCrudRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@SpringBootApplication
@Component
public class RBCResponse {
    private RestTemplate restTemplate;

    @Autowired
    private QuoteCrudRepository quoteCrudRepository;

    @Autowired
    public RBCResponse(QuoteCrudRepository quoteCrudRepository) {
        this.quoteCrudRepository = quoteCrudRepository;
        this.restTemplate = restTemplate();
    }

    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(RBCResponse.class, args);
    }

    public String[] getResponse() {
        try {
            String url = "http://export.rbc.ru/free/selt.0/free.fcgi?period=DAILY&tickers=USD000000TOD&separator=," +
                    "&data_format=BROWSER&lastdays=";
            ResponseEntity<String> response = restTemplate.getForEntity(url + "30", String.class);
            assert (response.getStatusCode().equals(HttpStatus.OK));
            return Objects.requireNonNull(response.getBody()).split("\n");
        } catch (Exception e) {
            // In case rbc service doesn't response.
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String[] output = new String[14];
            for (int i = 0; i < 14; i++) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, -1);
                String dateString = dateFormat.format(c.getTime());
                String rate = Double.toString((Math.random())*(90-70)+70);
                output[i] = "RUB," + dateString + "," + rate + "\n";
            }
            return output;
        }
    }

    @SuppressWarnings("WrapperTypeMayBePrimitive")
    Double getMax(String[] lines) {
        Double maxRate = 0.0;
        for (String line1 : lines) {
            String[] line = line1.split(",");
            maxRate = Math.max(maxRate, Double.parseDouble(line[line.length - 1]));
        }
        return maxRate;
    }

    String array2String(ArrayList<AbstractMap.SimpleEntry<Double, Long>> response) {
        StringBuilder answer = new StringBuilder();
        for (AbstractMap.SimpleEntry<Double, Long> item : response) {
            answer.append(item.getKey().toString());
            answer.append("=");
            answer.append(item.getValue().toString());
            answer.append(",");
        }
        String output = answer.toString();
        return output.substring(0, output.length() - 1);
    }

    ArrayList<AbstractMap.SimpleEntry<Double, Long>> getValues() throws ParseException {
        ArrayList<AbstractMap.SimpleEntry<Double, Long>> values = new ArrayList<>(30);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5:00"));
        for (String line1 : getResponse()) {
            String[] line = line1.split(",");
            Date tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(line[1]);
            cal.setTime(tempDate);
            AbstractMap.SimpleEntry<Double, Long> params = new AbstractMap.SimpleEntry <> (
                    Double.parseDouble(line[line.length - 1]), cal.getTimeInMillis() / 1000L
            );
            values.add(params);
        }
        return values;
    }

    @Transactional
    private void saveQuote(Double quote) {
        Date date = Calendar.getInstance().getTime();
        quoteCrudRepository.save(new Quote(quote, date));
    }

    @Transactional
    private Optional<Double> getQuoteByDate(Date date) {
        String dateString = Quote.dateFormat(date);
        Optional<Quote> rate = quoteCrudRepository.findByDate(dateString);
        return rate.map(Quote::getQuote);
    }

    @Transactional
    Double getMaxQuoteMonth() {
//        return 0.0;
        Optional<Double> maxQuote = getQuoteByDate(Calendar.getInstance().getTime());
        System.out.println(maxQuote);
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
