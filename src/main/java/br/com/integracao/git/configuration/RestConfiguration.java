package br.com.integracao.git.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {

    @Bean("restTemplate")
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}