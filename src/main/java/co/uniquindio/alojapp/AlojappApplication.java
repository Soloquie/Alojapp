package co.uniquindio.alojapp;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AlojappApplication {

	@Bean
	ApplicationRunner logMappings(org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping mapping) {
		return args -> mapping.getHandlerMethods().forEach((info, method) ->
				System.out.println(info + " -> " + method)
		);
	}

	public static void main(String[] args) {
		SpringApplication.run(AlojappApplication.class, args);
	}

}
