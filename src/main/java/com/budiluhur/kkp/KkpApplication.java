package com.budiluhur.kkp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class KkpApplication {
	
	public static final String SECURITY_KEY_FIELD 	= "Key";
	
	/**
	 * @return list of security parameters
	 */
	private static List<Parameter> getSecurityParameters() {
		return new ArrayList<>(Arrays.asList(
			new ParameterBuilder()
				.name(SECURITY_KEY_FIELD)
				.description("Security Key")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(true).build()));
	}

	public static void main(String[] args) {
		SpringApplication.run(KkpApplication.class, args);
	}
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.budiluhur.kkp.controller")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo()).globalOperationParameters(getSecurityParameters());
	}
	private ApiInfo apiInfo() {
		Contact contact = new Contact("Muhamad Satria Ihza", "some url", "mhmmdihza@gmail.com");
		return new ApiInfo("KKP BL REST API", "Some custom description of API.", "API TOS", "Terms of service",
				contact, "License of API", "API license URL");
	}
	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
