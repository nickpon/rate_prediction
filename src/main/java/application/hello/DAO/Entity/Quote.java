package application.hello.DAO.Entity;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name="Quote")
public class Quote {

    private final static DateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Double quote;
    private String date;

    protected Quote() {}

    public Quote(Double quote, Date date) {
        this.quote = quote;
        this.date = dateFormater.format(date);
    }

    public static String dateFormat(Date date) {
        return dateFormater.format(date);
    }

    public Double getQuote() {
        return quote;
    }
}


