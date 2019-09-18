package jose.rodriguez.everis.peru.app.serviceImplement;

import static org.mockito.Mockito.when;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import jose.rodriguez.everis.peru.app.models.dao.StudentDao;
import jose.rodriguez.everis.peru.app.models.document.Student;
import jose.rodriguez.everis.peru.app.models.service.implement.StudentServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)

public class testServiceImplement {
  


  @Mock
  private StudentDao studentDao;

  @InjectMocks
  private StudentServiceImpl studentService;

  


  private void assertResults(Publisher<Student> publisher, Student... expectedProducts) {
    StepVerifier.create(publisher).expectNext(expectedProducts).verifyComplete();
  }


  @Test
  public void findAll() {
    Student p = new Student();
    
    p.setName("Ángel");
    p.setLastName("Diam");
    p.setGender("M");
    p.setDate(new Date());
    p.setTypeDocument("dni");
    p.setDocument(58485888);
    when(studentService.findAll()).thenReturn(Flux.just(p));
    Flux<Student> actual = studentService.findAll();
    assertResults(actual, p);
  }


  @Test
  public void idexisting() {
    Student p = new Student();
    p.setName("Mae");
    p.setLastName("zoeli");
    p.setGender("F");
    p.setDate(new Date());
    p.setTypeDocument("dni");
    p.setDocument(47232312);
    when(studentDao.findById(p.getId())).thenReturn(Mono.just(p));
    Mono<Student> actual = studentService.findById(p.getId());
    assertResults(actual, p);
  }


  @Test
  public void findById_when_ID_NO_exist() {
    Student p = new Student();
    p.setId("iiiiiiiii");
    p.setName("Mae");
    p.setLastName("zoeli");
    p.setGender("F");
    p.setDate(new Date());
    p.setTypeDocument("dni");
    p.setDocument(96895756);
    when(studentDao.findById(p.getId())).thenReturn(Mono.empty());
    Mono<Student> actual = studentService.findById(p.getId());
    assertResults(actual);
  }



  @Test
  public void save() {
    Student p = new Student();
    p.setId("iiiiiiiii");
    p.setName("Mae");
    p.setLastName("zoeli");
    p.setGender("F");
    p.setDate(new Date());
    p.setTypeDocument("dni");
    p.setDocument(96895756);
    when(studentDao.save(p)).thenReturn(Mono.just(p));
    Mono<Student> actual = studentService.save(p);
    assertResults(actual, p);
  }



  @Test
  public void delete() {
    Student p = new Student();
    p.setId("iiiiii");
    p.setName("Mae");
    p.setLastName("zoeli");
    p.setDate(new Date());
    p.setTypeDocument("dni");
    p.setDocument(96895756);
    when(studentDao.delete(p)).thenReturn(Mono.empty());
  }


  @Test
  public void findFullName() {
    Student p = new Student();
    p.setId("iiiiiiii");
    p.setName("Mae");
    p.setLastName("zoeli");
    p.setDate(new Date());
    p.setTypeDocument("dni");
    p.setDocument(96895756);
    final String name = "736723727";
    when(studentDao.findByName(name)).thenReturn(Mono.just(p));
    Mono<Student> actual = studentService.findByName(name);
    assertResults(actual, p);
  }


  
  
  

}
