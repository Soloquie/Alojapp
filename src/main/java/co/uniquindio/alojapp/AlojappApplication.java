package co.uniquindio.alojapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class AlojappApplication {

	@Bean
	CommandLineRunner printMappings(ApplicationContext ctx) {
		return args -> {
			var rm = ctx.getBean(RequestMappingHandlerMapping.class);
			rm.getHandlerMethods().forEach((info, method) ->
					System.out.println(info + " -> " + method)
			);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(AlojappApplication.class, args);
	}

}
