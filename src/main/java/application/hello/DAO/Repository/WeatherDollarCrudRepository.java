package application.hello.DAO.Repository;

import application.hello.DAO.Entity.Quote;
import application.hello.DAO.Entity.WeatherDollar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherDollarCrudRepository extends CrudRepository<WeatherDollar, Long> {
    Optional<Quote> findByDate(String dateString);
}
