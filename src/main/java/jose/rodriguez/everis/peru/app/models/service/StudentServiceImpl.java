package jose.rodriguez.everis.peru.app.models.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jose.rodriguez.everis.peru.app.models.dao.StudentDao;
import jose.rodriguez.everis.peru.app.models.document.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao dao;

	@Override
	public Flux<Student> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}


	@Override
	public Mono<Student> findById(String id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public Mono<Student> save(Student students) {
		// TODO Auto-generated method stub
		return dao.save(students);
	}

	@Override
	public Mono<Void> delete(Student students) {
		// TODO Auto-generated method stub
		return dao.delete(students);
	}



	@Override
	public Flux<Student> findByName(String name) {
		
		return dao.findByName(name);
	}


	@Override
	public Mono<Student>findByDocument(int document) {
	
		return dao.findByDocument(document);
	}


	@Override
	public Flux<Student> findByDateBetween(Date date, Date date1) {
		
		return dao.findByDateBetween(date, date1);
	}








	
	
}
