package jose.rodriguez.everis.peru.app.models.dao;

import java.util.Date;
import jose.rodriguez.everis.peru.app.models.document.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**.
 * @author José LQ Rodriguez
 *
 */
public interface StudentDao extends ReactiveMongoRepository<Student, String> {
  Mono<Student> findByName(String name);

  Mono<Student> findByDocument(int document);

  Flux<Student> findByDateBetween(Date date, Date date1);

}
