package jose.rodriguez.everis.peru.app;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import jose.rodriguez.everis.peru.app.handlers.StudentsHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class FunctionRouters {


  /**.
   * Rutas para los métodos handler
   * @return
   */
  @Bean
  public RouterFunction<ServerResponse> routes(StudentsHandler handler) {
    return route(GET("/api/students").or(GET("/api/v1.0.0/students")), handler::findAll)
        .andRoute(GET("/api/students/{id}").or(GET("/api/v1.0.0/students/{id}")), handler::findById)
        .andRoute(POST("/api/students").or(GET("/api/v1.0.0/students")), handler::save)
        .andRoute(PUT("/api/students/{id}").or(PUT("/api/v1.0.0/students/{id}")), handler::update)
        .andRoute(DELETE("/api/students/{id}").or(DELETE("/api/v1.0.0/students/{id}")),
            handler::delete);

  }


}
