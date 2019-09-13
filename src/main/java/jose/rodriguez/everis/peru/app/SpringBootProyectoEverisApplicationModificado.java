package jose.rodriguez.everis.peru.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import jose.rodriguez.everis.peru.app.models.document.Student;
import jose.rodriguez.everis.peru.app.models.service.StudentService;
import reactor.core.publisher.Flux;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;


@SpringBootApplication
@EnableSwagger2WebFlux
public class SpringBootProyectoEverisApplicationModificado implements CommandLineRunner {


	@Autowired
	private StudentService service;
	
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootProyectoEverisApplicationModificado.class);

	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootProyectoEverisApplicationModificado.class);
		
	}
	

	
	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("students").subscribe();
	

		
		Flux.just(new Student("Jose", "Rodriguez","M","Dni",98574858),
					new Student("Elena", "Marin","F","Dni",98574453),
					new Student("Katty", "Garcia","F","Dni",98574212),
					new Student("Yulia", "De la Riva","F","Dni",98574111),
					new Student("Joan", "Flux","M","Dni",98574323),
					new Student("Pedro", "Miffling","M","Dni",98574333),
					new Student("Royer", "Sanchez","M","Dni",98574000)
				)
		.flatMap(std -> {
			std.setDate(new Date());
			return service.save(std);
			})
		.subscribe(st -> log.info("Insert: " + st.getId() + " " + st.getLastName()));
		
	
		
	
	}
	
	
	
}
