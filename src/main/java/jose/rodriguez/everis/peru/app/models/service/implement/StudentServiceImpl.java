package jose.rodriguez.everis.peru.app.models.service.implement;

import java.util.Date;
import jose.rodriguez.everis.peru.app.models.dao.StudentDao;
import jose.rodriguez.everis.peru.app.models.document.Student;
import jose.rodriguez.everis.peru.app.models.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class StudentServiceImpl implements StudentService {

  @Autowired
  private StudentDao dao;

  @Override
  public Flux<Student> findAll() {
   
    return dao.findAll();
  }


  @Override
  public Mono<Student> findById(String id) {
    
    return dao.findById(id);
  }

  @Override
  public Mono<Student> save(Student students) {
   
    return dao.save(students);
  }

  @Override
  public Mono<Void> delete(Student students) {
    
    return dao.delete(students);
  }


  @Override
  public Mono<Student> findByDocument(int document) {

    return dao.findByDocument(document);
  }


  @Override
  public Flux<Student> findByDateBetween(Date date, Date date1) {

    return dao.findByDateBetween(date, date1);
  }

  @Override
  public Mono<Student> findByName(String name) {
    
    return dao.findByName(name);
  }



}
