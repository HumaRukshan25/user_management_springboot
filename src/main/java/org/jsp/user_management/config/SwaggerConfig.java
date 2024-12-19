package org.jsp.user_management.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	
//swagger is an api testing  tool and documentation tool
//	to use it give http://localhost:8080/swagger-ui/index.html
	
	@Bean
 OpenAPI usersMicroserviceOpenAPI() { //no public bean is used it will give default
		Server localhost = new Server();
		localhost.setUrl("http://localhost:8080");
		localhost.setDescription("Development environment");
		Contact contact = new Contact();
		contact.setEmail("rukshanhuma2508@gmail.com");
		contact.setName("User Management");
		contact.setUrl("http://localhost:8080");
		
		License mitLicense = new License().name("MIT License").url("http://localhost:8080");
		Info info = new Info().title("User Management Application RESTful Web Service documentation").version("1.0")
				.contact(contact).description("This API exposes endpoints to manage the User Managemenr Application.")
				.termsOfService("http://localhost:8080").license(mitLicense);
		return new OpenAPI().info(info).servers(List.of(localhost));
	}

}



