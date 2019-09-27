package jose.rodriguez.everis.peru.app;




import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;


/**
 * .
 * 
 * @author José LQ Rodriguez
 *
 */

@EnableSwagger2WebFlux
@EnableEurekaClient
@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootProyectoEverisApplicationModificado implements CommandLineRunner {




  /**
   * . run
   */
  public static void main(String[] args) {

    SpringApplication.run(SpringBootProyectoEverisApplicationModificado.class);

  }



  @Override
  public void run(String... args) throws Exception {



    
  }

}
