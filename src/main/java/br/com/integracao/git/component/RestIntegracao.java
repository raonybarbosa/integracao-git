package br.com.integracao.git.component;

import br.com.integracao.git.dto.FileGit;
import br.com.integracao.git.response.Response;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestIntegracao {

    @Value("${git.url.api}15661710/repository/files/Script.sql/blame?ref=master")
    private String rotaLogger;

    private HttpHeaders headers;
    private Gson gson;
    private RestTemplate restConsumer;

    public RestIntegracao(@Qualifier("restTemplate") RestTemplate restConsumer) {
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.headers.set("private-token", "PB3pRAS1RRFzcnm3ezB3");
        this.gson = new Gson();
        this.restConsumer = restConsumer;
    }

    public Response fazerRequisicao() {
        Response responseLogger = new Response();
        String jsonRequest = gson.toJson(responseLogger);
        HttpEntity<String> requestEntity = new HttpEntity(jsonRequest, headers);
        return restConsumer.exchange(rotaLogger, HttpMethod.GET, requestEntity, Response.class).getBody();
    }

    public String fazerRequisicao2() {
        FileGit fileGit = new FileGit();
        String jsonRequest = gson.toJson(fileGit);
        HttpEntity<String> requestEntity = new HttpEntity(jsonRequest, headers);
        Map<String, String> vars = new HashMap<>();
        vars.put("id", "15661710");
        //vars.put("arquivo", "DDL/arquivo_02-01-2020-09-48-30.sql");
        vars.put("arquivo", "DDL%2Farquivo_02-01-2020-09-48-30.sql");
        vars.put("brach", "develop");
        //https://gitlab.com/api/v4/projects/{id}/repository/files/DDL%2Farquivo_02-01-2020-09-48-30.sql?ref=develop
        //https://gitlab.com/raonybarbosa/integracaogit/-/raw/develop/DDL/arquivo_02-01-2020-09-48-30.sql?inline=false
        //https://gitlab.com/api/v4/projects/{id}/repository/files/{arquivo}?ref={brach}
        //return restConsumer.getForObject("https://gitlab.com/raonybarbosa/integracaogit/-/raw/develop/DDL/arquivo_02-01-2020-09-48-30.sql?inline=false",
        //        FileGit.class);
        return restConsumer.exchange("https://gitlab.com/raonybarbosa/integracaogit/-/raw/develop/DDL/arquivo_02-01-2020-09-48-30.sql?inline=false", HttpMethod.GET, requestEntity, String.class).getBody();
    }
}
