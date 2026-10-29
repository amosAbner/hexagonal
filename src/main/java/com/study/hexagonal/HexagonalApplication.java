package com.study.hexagonal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.server.context.WebServerApplicationContext;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;

@EnableFeignClients
@SpringBootApplication
public class HexagonalApplication {

	private static final Logger logger = LoggerFactory.getLogger(HexagonalApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HexagonalApplication.class, args);
	}

	@EventListener
	public void onApplicationReady(ApplicationReadyEvent event) {
		if (!(event.getApplicationContext() instanceof WebServerApplicationContext webContext)) {
			return;
		}

		var environment = event.getApplicationContext().getEnvironment();
		var contextPath = environment.getProperty("server.servlet.context-path", "");
		var baseUrl = "http://localhost:" + webContext.getWebServer().getPort() + contextPath;
		var swaggerPath = environment.getProperty("springdoc.swagger-ui.path", "/swagger-ui.html");

		logger.info("""

				============================================================
				Aplicação iniciada com sucesso!
				Swagger UI: {}
				Host da API: {}
				============================================================
				""", baseUrl + swaggerPath, baseUrl);
	}

}
