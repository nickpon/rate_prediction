package application.hello.DAO.Entity;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="WeatherDollar")
public class WeatherDollar {
//    private final static DateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String dollar;
    private String date;
    private String temperature;

    protected WeatherDollar() {}

    public WeatherDollar(String dollar, Date date, String temperature) {
        this.dollar = dollar;
//        this.date = dateFormater.format(date);
        this.temperature = temperature;
    }

//    public static String dateFormat(Date date) {
//        return dateFormater.format(date);
//    }

    public String getDollar() {
        return dollar;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }
}