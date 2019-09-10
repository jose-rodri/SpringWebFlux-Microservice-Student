package jose.rodriguez.everis.peru.app.controllers;

import java.net.URI;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jose.rodriguez.everis.peru.app.models.document.Student;
import jose.rodriguez.everis.peru.app.models.service.StudentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/students")
public class StudentsController {

	@Autowired
	private StudentService service;
	
	
	//listar estudiante
	@GetMapping
	public Mono <ResponseEntity<Flux<Student>>>listar(){
		return Mono.just(
	
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll()));
	}

	//buscar un estudiante por codigo
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Student>> ver(@PathVariable String id ){
		return service.findById(id).map( p -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	//Actualizar un estudiante
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Student>>editar(@RequestBody Student student , @PathVariable String id){
		return service.findById(id).flatMap(p ->{
			p.setName(student.getName());
			p.setLastName(student.getLastName());
			p.setGender(student.getGender());
			p.setTypeDocument(student.getTypeDocument());
			p.setDocument(student.getDocument());
			return service.save(p);
		}).map(p -> ResponseEntity.created(URI.create("/api/students/".concat(p.getId())))
				.body(p)).defaultIfEmpty(ResponseEntity.notFound().build());
		
		
	}
	
	//Eliminar un estudiante
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>>eliminar(@PathVariable String id){
		return service.findById(id).flatMap(
				p ->{
					return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
					
				}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	
}
