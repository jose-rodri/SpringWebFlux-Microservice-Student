package jose.rodriguez.everis.peru.app;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


import jose.rodriguez.everis.peru.app.handlers.StudentsHandler;

@Configuration
public class FunctionRouters {


	@Bean // se configura las rutas
	public RouterFunction<ServerResponse> routes(StudentsHandler handler){
		/*     //vesion 2 Handler
		return route(GET("/api/v2/students").or(GET("/api/v2/students")), handler::listar)
				.andRoute(GET("/api/v2/students/{id}"), handler::ver)
				.andRoute(PUT("/api/v2/students/{id}"), handler::editar)
				.andRoute(DELETE("/api/v2/students/{id}"), handler::eliminar);
				
			*/	
						
		
		return route(GET("/api/students").or(GET("/api/students")), handler::listar)
				.andRoute(GET("/api/students/{id}"), handler::ver)
				.andRoute(PUT("/api/students/{id}"), handler::editar)
				.andRoute(DELETE("/api/students/{id}"), handler::eliminar);
				 
		 }
	
	
}
