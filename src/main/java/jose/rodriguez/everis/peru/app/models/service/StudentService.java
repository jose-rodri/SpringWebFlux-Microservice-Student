package jose.rodriguez.everis.peru.app.models.service;

import java.util.Date;
import jose.rodriguez.everis.peru.app.models.document.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {

  public Flux<Student> findAll();

 // public Mono<Student> obtenerPorNombre(String name);

  public Mono<Student>findByName(String name);
  public Mono<Student> findById(String id);

  public Mono<Student> save(Student students);

  public Mono<Void> delete(Student students);

  public Mono<Student> findByDocument(int document);

  public Flux<Student> findByDateBetween(Date date, Date date1);


}
