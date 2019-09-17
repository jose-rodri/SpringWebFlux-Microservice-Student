package jose.rodriguez.everis.peru.app.handlers;

import java.net.URI;
import java.util.Date;
import jose.rodriguez.everis.peru.app.models.document.Student;
import jose.rodriguez.everis.peru.app.models.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


/**.
 * @author jquispro
 * PROGRAMACION  FUNCIONAL USO OPCIONAL
 */
@Component
public class StudentsHandler {

  @Autowired
  private StudentService service;


  
  /**.
   * Médito lista
   */
  public Mono<ServerResponse> findAll(ServerRequest request) {

    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(service.findAll(),
        Student.class);
  }

 
  /**.
   * Método filtro
   */
  public Mono<ServerResponse>findById(ServerRequest request) {
    String id = request.pathVariable("id");
    return service.findById(id).flatMap(p -> ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(p)))
        .switchIfEmpty(ServerResponse.notFound().build());

  }

  
  /**.
   *Método crear
   */
  public Mono<ServerResponse>save(ServerRequest request) {
    Mono<Student> student = request.bodyToMono(Student.class);
    return student.flatMap(p -> {
      if (p.getDate() == null) {                            
        p.setDate(new Date());
      }
      return service.save(p);
    }).flatMap(p -> ServerResponse.created(URI.create("/api/students/".concat(p.getId())))
        .contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(p)));

  }

  /**.
   * Método actualizar
   */
  public Mono<ServerResponse>update(ServerRequest request) {
    Mono<Student> student = request.bodyToMono(Student.class); // req
    String id = request.pathVariable("id");

    Mono<Student> studentDb = service.findById(id);

    return studentDb.zipWith(student, (db, req) -> {
      db.setName(req.getName());
      db.setLastName(req.getLastName());
      db.setGender(req.getGender());
      db.setTypeDocument(req.getTypeDocument());
      db.setDocument(req.getDocument());

      return db;
    }).flatMap(p -> ServerResponse.created(URI.create("/api/student/".concat(p.getId())))
        .contentType(MediaType.APPLICATION_JSON_UTF8).body(service.save(p), Student.class))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  /**.
   * Método Eliminar
   */
 
  public Mono<ServerResponse>delete(ServerRequest request) {

    String id = request.pathVariable("id");
    Mono<Student> studentdb = service.findById(id);
    return studentdb.flatMap(p -> service.delete(p).then(ServerResponse.noContent().build()))
        .switchIfEmpty(ServerResponse.notFound().build());

  }


}
