package br.com.integracao.git.service.impl;

import br.com.integracao.git.response.Response;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestIntegracao {

    @Value("https://gitlab.com/api/v4/projects/15661710/repository/files/Script.sql/blame?ref=master")
    private String rotaLogger;

    private HttpHeaders headers;
    private Gson gson;
    private RestTemplate restConsumer;

    public RestIntegracao(@Qualifier("restTemplate") RestTemplate restConsumer) {
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.headers.set("PRIVATE-TOKEN", "PB3pRAS1RRFzcnm3ezB3");
        this.gson = new Gson();
        this.restConsumer = restConsumer;
    }

    public Response fazerRequisicao() {
        Response responseLogger = new Response();
        String jsonRequest = gson.toJson(responseLogger);
        HttpEntity<String> requestEntity = new HttpEntity(jsonRequest, headers);
        return restConsumer.exchange(rotaLogger, HttpMethod.GET, requestEntity, Response.class).getBody();
    }
}
