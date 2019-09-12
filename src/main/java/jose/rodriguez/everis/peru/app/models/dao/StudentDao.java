package jose.rodriguez.everis.peru.app.models.dao;

import jose.rodriguez.everis.peru.app.models.document.Student;

import java.util.Date;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentDao extends ReactiveMongoRepository<Student, String> {

  Flux<Student> findByName(String name);
  
  Mono<Student> findByDocument(int document);
  
  Flux<Student>findByDateBetween(Date date, Date date1);

}
