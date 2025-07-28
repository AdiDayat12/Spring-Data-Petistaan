package com.abhishekvermaa10;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.abhishekvermaa10.service.OwnerService;
import com.abhishekvermaa10.service.PetService;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@OpenAPIDefinition(
		info = @Info(
				title = "Petistaan API",
				version = "1.0.0",
				description = "API endpoints for handling owner & pet data.",
				contact = @Contact(
						name = "Adi Hidayat",
						email = "adilinan72@gmail.com",
						url = "https://github.com/AdiDayat12/"
				)
		)
)
@RequiredArgsConstructor
@PropertySource("classpath:messages.properties")
@SpringBootApplication
public class Demo {
	
	private final OwnerService ownerService;
	private final PetService petService;
	private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);

	public static void main(String[] args) {
		SpringApplication.run(Demo.class, args);
	}


}
