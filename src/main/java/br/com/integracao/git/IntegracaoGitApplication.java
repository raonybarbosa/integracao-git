package br.com.integracao.git;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IntegracaoGitApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntegracaoGitApplication.class, args);
    }
}