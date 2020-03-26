package ru.sbt.rbc.DAO.Entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name="Quote")
public class Quote {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Double quote;
    private String date;

    protected Quote() {}

    public Quote(Double quote, Date date) {
        this.quote = quote;
        this.date = new SimpleDateFormat("yyyy/MM/dd").format(date);
    }

    public static String dateFormat(Date date) {
        return new SimpleDateFormat("yyyy/MM/dd").format(date);
    }

    public Double getQuote() {
        return quote;
    }
}


