package jose.rodriguez.everis.peru.app.controllers;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import jose.rodriguez.everis.peru.app.models.dao.StudentDao;
import jose.rodriguez.everis.peru.app.models.document.Student;
import jose.rodriguez.everis.peru.app.models.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**.
 * prueba de rama students
 */
@RestController
@RequestMapping("/api/everis/students")
public class StudentsController {



  @Autowired
  private StudentService service;

  @Autowired
  private StudentDao dao;

 
  /**.
   * Método listar
   */
  @GetMapping
  public Mono<ResponseEntity<Flux<Student>>> findAll() {
    return Mono.just(

        ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(service.findAll()));
  }

 
  

  /**
   * . Método Crear
   */
  @PostMapping
  public Mono<ResponseEntity<Student>> save(@RequestBody Student student) {
    if (student.getDate() == null) {
      student.setDate(new Date());
    }
    return service.save(student)
        .map(p -> ResponseEntity.created(URI.create("/api/everis/students/".concat(p.getId())))
            .contentType(MediaType.APPLICATION_JSON_UTF8).body(p));

  }

  
  
  /**.
   * Método filtrar por codigo
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<Student>> findById(@PathVariable String id) {
    return service.findById(id)
        .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }



  /**.
   * Método filtro por nombre
   */
  @GetMapping("name/{name}")
  public Mono<Student> findByName(@PathVariable("name") String name) {
    return service.findByName(name);
  }


  /**.
   * Método filtro por documento
   */
  @GetMapping("dni/{document}")
  public Mono<Student> findByDocument(@PathVariable("document") int document) {
    return dao.findByDocument(document);
  }



  /**.
   * Método filtro por fecha
   */
  @GetMapping("date/{date}/{date1}")
  public Flux<Student> findByDateBetween(
      @PathVariable("date") @DateTimeFormat(iso = ISO.DATE) Date date,
      @PathVariable("date1") @DateTimeFormat(iso = ISO.DATE) Date date1) {
      return service.findByDateBetween(date, date1);
  }



  /**.
   * Método Actualizar
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Student>> update(@RequestBody Student student,
      @PathVariable String id) {
    return service.findById(id).flatMap(p -> {
      p.setName(student.getName());
      p.setLastName(student.getLastName());
      p.setGender(student.getGender());
      p.setTypeDocument(student.getTypeDocument());
      p.setDocument(student.getDocument());
      return service.save(p);
    }).map(
        p -> ResponseEntity.created(URI.create("/api/everis/students/".concat(p.getId()))).body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());


  }



  
 
  /**.
   * Método Eliminar
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    return service.findById(id).flatMap(p -> {
      return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));

    }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }



}
