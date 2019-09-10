package jose.rodriguez.everis.peru.app.handlers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;


import jose.rodriguez.everis.peru.app.models.document.Student;
import jose.rodriguez.everis.peru.app.models.service.StudentService;
import reactor.core.publisher.Mono;


@Component
public class StudentsHandler {

	
	@Autowired
	private StudentService service;
	
	
	//listar estudiante
	public Mono<ServerResponse> listar(ServerRequest request){
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll(), Student.class);
	}
	
	//buscar estudiante por codigo
	public Mono<ServerResponse> ver(ServerRequest request){
		String id= request.pathVariable("id");
		return service.findById(id).flatMap(p -> ServerResponse.ok()
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(BodyInserters.fromObject(p)))
			.switchIfEmpty(ServerResponse.notFound().build());
				
	}
	
	
	//actualizar estudiate
	public Mono<ServerResponse> editar(ServerRequest request){
		Mono<Student> student = request.bodyToMono(Student.class); //req
		String id = request.pathVariable("id");
		
		Mono<Student> studentDb = service.findById(id);
		
		return studentDb.zipWith(student , (db , req) ->{
			db.setName(req.getName());
			db.setLastName(req.getLastName());
			db.setGender(req.getGender());
			db.setTypeDocument(req.getTypeDocument());
			db.setDocument(req.getDocument());
			
			return db;
			}).flatMap(p -> ServerResponse.created(URI.create("/api/v2/student/".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.save(p), Student.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	
	//Elimiar estudiante
	public Mono<ServerResponse> eliminar(ServerRequest request){
		
		String id = request.pathVariable("id");
		Mono<Student> studentdb = service.findById(id);
		return studentdb.flatMap(p -> service.delete(p)
				.then(ServerResponse.noContent().build()))
				.switchIfEmpty(ServerResponse.notFound().build());
		
	}
	
	
}
