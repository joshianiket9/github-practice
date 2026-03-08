package com.cardworks.configuration;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfiguration {

	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> testPortCustomizer() {
		return factory ->factory.setPort(8181);
	}
	
}
