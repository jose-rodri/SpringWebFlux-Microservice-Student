package jose.rodriguez.everis.peru.app.controller;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import jose.rodriguez.everis.peru.app.models.document.Student;
import jose.rodriguez.everis.peru.app.models.service.StudentService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class testController {

  @Autowired
  private StudentService service;

  @Autowired
  private WebTestClient client;


  @Test
  public void findAllTest() {
    client.get().uri("/api/everis/students").accept(MediaType.APPLICATION_JSON_UTF8).exchange()
        .expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Student.class).consumeWith(response -> {
          List<Student> student = response.getResponseBody();
          student.forEach(p -> {
            System.out.println(p.getName());
          });
          Assertions.assertThat(student.size() > 0).isTrue();
        });

  }

 

  @Test
  public void findByIdTest() {

    Student student = service.findByName("Katty").block();

    client.get().uri("/api/everis/students/{id}", Collections.singletonMap("id", student.getId()))
        .accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk().expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8).expectBody(Student.class)
        .consumeWith(response -> {
          Student s = response.getResponseBody();
          Assertions.assertThat(s.getId()).isNotEmpty();
          Assertions.assertThat(s.getId().length() > 0).isTrue();
          Assertions.assertThat(s.getName()).isEqualTo("Katty");

        });

  }

 
  
  @Test
  public void updateTest() {

    Student student = service.findByName("Elena").block();
    Student studentEditado = new Student("Elena", "Mendeil", "F",new Date(), "dni", 58788878);
    client.put().uri("/api/everis/students/{id}", Collections.singletonMap("id", student.getId()))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .body(Mono.just(studentEditado), Student.class)
        .exchange().expectStatus().isCreated()
        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody()
        .jsonPath("$.id").isNotEmpty()
        .jsonPath("$.name").isEqualTo("Elena")
        .jsonPath("$.lastName").isEqualTo("Mendeil")
        .jsonPath("$.gender").isEqualTo("F")
        .jsonPath("$.typeDocument").isEqualTo("dni")
        .jsonPath("$.document").isEqualTo(58788878);

  }


  



  @Test
  public void eliminarTest() {
    Student student = service.findByName("Pedro").block();
    client.delete()
        .uri("/api/everis/students/{id}", Collections.singletonMap("id", student.getId()))
        .exchange().expectStatus().isNoContent().expectBody().isEmpty();
  }



 

  @Test
  public void findByNameTest() {

    Student student = service.findByName("Elena").block();

    client.get()
        .uri("/api/everis/students/name/{name}",
         Collections.singletonMap("name", student.getName()))
        .accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk().expectBody()
        .jsonPath("$.name").isEqualTo("Elena");
        

  }

  
  
  @Test
  public void findByDocumentTest() {

    Student student = service.findByName("Jose").block();

    client.get()
        .uri("/api/everis/students/dni/{document}",
            Collections.singletonMap("document", student.getDocument()))
        .accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk().expectBody()
        .jsonPath("$.document").isEqualTo(98574858);

  }

  
  /**
   * . s
   */
  @Test
  public void saveTest() {
    Student student = new Student("Julio", "Zeu", "M", new Date(), "dni", 87774444);
    client.post().uri("/api/everis/students").contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(student), Student.class).exchange()
        .expectStatus().isCreated().expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody().jsonPath("$.id").isNotEmpty().jsonPath("$.name").isEqualTo("Julio");

  }
 

  /*
   * @Test public void FindByDateBetweenTest() { Flux<List<Student>> student = (Flux<List<Student>>)
   * service.findByDateBetween(new Date() , new Date()).collectList().block(); //Student student =
   * service.findByDocument(98574323).block(); client.get()
   * .uri("/api/everis/students/fecha/{date}/{date1}" , Collections.singletonMap("date","date1",
   * ((Date) student).getDate())) .accept(MediaType.APPLICATION_JSON_UTF8) .exchange()
   * .expectStatus().isOk() .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
   * .expectBody(Student.class) .consumeWith(response ->{ Student s = response.getResponseBody();
   * Assertions.assertThat(s.getId()).isNotEmpty();
   * Assertions.assertThat(s.getId().length()>0).isTrue();
   * Assertions.assertThat(s.getDate()).isEqualTo(98574212);
   * 
   * });
   * 
   * }
   * 
   */

}
