package jose.rodriguez.everis.peru.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import jose.rodriguez.everis.peru.app.models.document.Student;

public interface StudentDao extends ReactiveMongoRepository<Student, String>   {

}
