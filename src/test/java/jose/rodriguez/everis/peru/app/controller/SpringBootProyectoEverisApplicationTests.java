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
public class SpringBootProyectoEverisApplicationTests {

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

    Student student = service.findByName("Issac").block();
    if (student != null) {
      client.get().uri("/api/everis/students/{id}", Collections.singletonMap("id", student.getId()))
          .accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk().expectHeader()
          .contentType(MediaType.APPLICATION_JSON_UTF8).expectBody(Student.class)
          .consumeWith(response -> {
            Student p = response.getResponseBody();
            Assertions.assertThat(p.getId()).isNotEmpty();
            Assertions.assertThat(p.getId().length() > 0).isTrue();
            Assertions.assertThat(p.getName()).isEqualTo("Issac");

          });
    }
  }



  @Test
  public void findByNameTest() {

    Student student = service.findByName("Mae").block();
    if (student != null) {
      client.get()
          .uri("/api/everis/students/name/{name}",
              Collections.singletonMap("name", student.getName()))
          .accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk().expectBody()
          .jsonPath("$.name").isEqualTo("Mae");

    }
  }



  @Test
  public void findByDocumentTest() {

    Student student = service.findByName("Elena").block();
    if (student != null) {
      client.get()
          .uri("/api/everis/students/dni/{document}",
              Collections.singletonMap("document", student.getDocument()))
          .accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk().expectBody()
          .jsonPath("$.name").isEqualTo("Elena");
    }
  }


  /**
   * . s
   */
  @Test
  public void saveTest() {
    Student student = new Student("Julio", "Zeu", "M", new Date(), "dni", 87774444,15);
    client.post().uri("/api/everis/students").contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(student), Student.class).exchange()
        .expectStatus().isCreated().expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody().jsonPath("$.id").isNotEmpty().jsonPath("$.name").isEqualTo("Julio");

  }



  @Test
  public void updateTest() {
    Student student = service.findByName("Royer").block();
    if (student != null) {
      Student studentEditado = new Student("Royer", "Flux", "M", new Date(), "dni", 58788878,15);
      client.put().uri("/api/everis/students/{id}", Collections.singletonMap("id", student.getId()))
          .contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
          .body(Mono.just(studentEditado), Student.class).exchange().expectStatus().isCreated()
          .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8).expectBody().jsonPath("$.id")
          .isNotEmpty().jsonPath("$.name").isEqualTo("Royer").jsonPath("$.lastName")
          .isEqualTo("Flux").jsonPath("$.gender").isEqualTo("M").jsonPath("$.typeDocument")
          .isEqualTo("dni").jsonPath("$.document").isEqualTo(58788878);

    }
  }


  @Test
  public void eliminarTest() {

    Student student = service.findByName("Royer").block();
    if (student != null) {
      client.delete()
          .uri("/api/everis/students/{id}", Collections.singletonMap("id", student.getId()))
          .exchange().expectStatus().isNoContent().expectBody().isEmpty();
    }

  }
  //



}
