package application.hello.DAO.Repository;

import application.hello.DAO.Entity.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface QuoteCrudRepository extends CrudRepository<Quote, Long> {
    Optional<Quote> findByDate(String dateString);
}