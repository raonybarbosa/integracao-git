package br.com.integracao.git;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableAdminServer
public class IntegracaoGitApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntegracaoGitApplication.class, args);
    }
}