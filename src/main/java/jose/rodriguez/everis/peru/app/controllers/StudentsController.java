package jose.rodriguez.everis.peru.app.controllers;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import jose.rodriguez.everis.peru.app.models.dao.StudentDao;
import jose.rodriguez.everis.peru.app.models.document.Student;
import jose.rodriguez.everis.peru.app.models.service.StudentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//RESTU FULL USANDO REST-CONTROLLER

@RestController
@RequestMapping("/api/everis/students") 
public class StudentsController {


  
  @Autowired
  private StudentService service;

  @Autowired
  private StudentDao dao;

  //listar estudiante
  /*
   * */
  
  @GetMapping
  public Mono<ResponseEntity<Flux<Student>>> listar() {
    return Mono.just(
	
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll()));
	}

	/*
	 
	@PostMapping
	public Mono<ResponseEntity<Student>> crear( @RequestBody Student student){
		if(student.getDate()== null) {
			student.setDate(new Date());
		}
			return service.save(student).map(p -> ResponseEntity 
			.created(URI.create("/api/everis/students/".concat(p.getId())))
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(p));
		
	}
	
	 *  */
	//por terminar la validacion del error
	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> crear(@Valid @RequestBody Mono<Student> monoStudent){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		return monoStudent.flatMap(student ->{
			if(student.getDate()== null) {
				student.setDate(new Date());
			}
		
			
			return service.save(student).map(p ->{
				respuesta.put("Estudiante", p);
				respuesta.put("Mensaje", "Estudiante creado con éxit");
				respuesta.put("Fecha", new Date());
				return ResponseEntity
						.created(URI.create("/api/everis/students/".concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(respuesta);
				
						
			});
		}).onErrorResume(t -> {
			return Mono.just(t).cast(WebExchangeBindException.class)
					.flatMap(e -> Mono.just(e.getFieldErrors()))
					.flatMapMany(Flux::fromIterable)
					.map(fieldError -> "El campo "+fieldError.getField() + " " + fieldError.getDefaultMessage())
					.collectList()
					.flatMap(list -> {
						respuesta.put("errors", list);
						respuesta.put("timestamp", new Date());
						respuesta.put("status", HttpStatus.BAD_REQUEST.value());
						return Mono.just(ResponseEntity.badRequest().body(respuesta));
					});
							
		});
		
		
	}
	
			
	
	//buscar un estudiante por codigo
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Student>> ver(@PathVariable String id ){
		return service.findById(id).map( p -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	
	
	
	
	
	//buscar un estudiante por  de 
		@GetMapping("nombre/{name}")
		public Mono<Student> buscarNombre(@PathVariable("name") String name){
			return service.obtenerPorNombre(name);
		}
		
		
		@GetMapping("dni/{document}")
		public Mono<Student> buscarDocumento(@PathVariable("document") int document){
			return dao.findByDocument(document);
		}
		
		
		
		@GetMapping("fecha/{date}/{date1}")
		public Flux<Student> buscarFecha(@PathVariable("date")@DateTimeFormat( iso = ISO.DATE) Date date,
											   @PathVariable("date1")@DateTimeFormat( iso = ISO.DATE)  Date date1){
			return service.findByDateBetween(date, date1);
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
		}).map(p -> ResponseEntity.created(URI.create("/api/everis/students/".concat(p.getId())))
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
