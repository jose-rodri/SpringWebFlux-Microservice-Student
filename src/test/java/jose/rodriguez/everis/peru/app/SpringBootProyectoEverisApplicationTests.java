package jose.rodriguez.everis.peru.app;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;

import jose.rodriguez.everis.peru.app.models.document.Student;
import jose.rodriguez.everis.peru.app.models.service.StudentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
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
		client.get()
		.uri("/api/everis/students")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBodyList(Student.class)
		.consumeWith(response ->{
		List<Student> student = response.getResponseBody();	
			student.forEach(p ->{
				System.out.println(p.getName());
			});
			Assertions.assertThat(student.size()>0).isTrue();
		});
			
	}
	
	//test buscar por id
	
	@Test
	public void FindByIdTest() {
		
		Student student = service.obtenerPorNombre("Jose").block();
		
		client.get()
		.uri("/api/everis/students/{id}" , Collections.singletonMap("id", student.getId()))
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBody(Student.class)
		.consumeWith(response ->{
			Student s = response.getResponseBody();
			Assertions.assertThat(s.getId()).isNotEmpty();
			Assertions.assertThat(s.getId().length()>0).isTrue();
			Assertions.assertThat(s.getName()).isEqualTo("Jose");
			
		});
		
	}

	//test editar
	@Test
	public void editarTest() {
		
		Student student = service.obtenerPorNombre("Elena").block();
		Student studentEditado = new Student("Elena","Mendeil","F","dni", 58788878);
		client.put().uri("/api/everis/students/{id}", Collections.singletonMap("id", student.getId()))
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.body(Mono.just(studentEditado), Student.class)
		.exchange()
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBody()
		.jsonPath("$.id").isNotEmpty()
		.jsonPath("$.name").isEqualTo("Elena")
		.jsonPath("$.lastName").isEqualTo("Mendeil")
		.jsonPath("$.gender").isEqualTo("F")
		.jsonPath("$.typeDocument").isEqualTo("dni")
		.jsonPath("$.document").isEqualTo(58788878);
		
	}
	


	
	//test Eliminar
		
		

		@Test
		public void eliminarTest() {
			Student student = service.obtenerPorNombre("Pedro").block();
			client.delete()
			.uri("/api/everis/students/{id}", Collections.singletonMap("id", student.getId()))
			.exchange()
			.expectStatus().isNoContent()
			.expectBody()
			.isEmpty();
		}
	
	
		
		
		//test Buscar por nombre
		
		@Test
		public void FindByNameTest() {
			
			Student student = service.obtenerPorNombre("Joan").block();
			
			client.get()
			.uri("/api/everis/students/nombre/{name}" , Collections.singletonMap("name", student.getName()))
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBody(Student.class)
			.consumeWith(response ->{
				Student s = response.getResponseBody();
				Assertions.assertThat(s.getId()).isNotEmpty();
				Assertions.assertThat(s.getId().length()>0).isTrue();
				Assertions.assertThat(s.getName()).isEqualTo("Joan");
				
			});
			
		}
	
		
		/*
		 
	
		//Test crear 
		
		@Test
		public void crearTest() {
			Student student = new Student("Zen","Jaedon","Show","M","dni",, 98696632);
			client.post().uri("/api/everis/students")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(student), Student.class)
			.exchange()
			.expectStatus()
			.isCreated()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBody()
			.jsonPath("$.id").isNotEmpty()
			.jsonPath("$.name").isEqualTo("Zen");
				
			}
		
		//buscar por documento
		@Test
		public void FindByDocumentTest() {
			
			Student student = service.findByDocument(98574212) .block();
			//Student student = service.findByDocument(98574323).block();
			client.get()
			.uri("/api/everis/students/dni/{document}" , Collections.singletonMap("document", student.getDocument()))
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBody(Student.class)
			.consumeWith(response ->{
			Student s = response.getResponseBody();
				Assertions.assertThat(s.getId()).isNotEmpty();
				Assertions.assertThat(s.getId().length()>0).isTrue();
				Assertions.assertThat(s.getDocument()).isEqualTo(98574212);
				
			});
			
		}
		
		//buscar por fecha
		
		
		
				@Test
				public void FindByDateBetweenTest() {
					Flux<List<Student>> student = (Flux<List<Student>>) service.findByDateBetween(new Date() , new Date()).collectList().block();
					//Student student = service.findByDocument(98574323).block();
					client.get()
					.uri("/api/everis/students/fecha/{date}/{date1}" , Collections.singletonMap("date","date1", ((Date) student).getDate()))
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
					.expectBody(Student.class)
					.consumeWith(response ->{
					Student s = response.getResponseBody();
						Assertions.assertThat(s.getId()).isNotEmpty();
						Assertions.assertThat(s.getId().length()>0).isTrue();
						Assertions.assertThat(s.getDate()).isEqualTo(98574212);
						
					});
					
				}
				
				*/		
		
}
