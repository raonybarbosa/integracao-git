package br.com.integracao.git.component;

import br.com.integracao.git.dto.FileGit;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestIntegracao {

    private final HttpHeaders headers;
    private final Gson gson;
    private final RestTemplate restConsumer;
    @Value("${git.url.api}" + "${git.projeto.id}/repository/files/")
    private String urlGitLab;

    public RestIntegracao(@Qualifier("restTemplate") RestTemplate restConsumer, @Value("${git.token}") String tokenGit) {
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.headers.set("private-token", tokenGit);
        this.gson = new Gson();
        this.restConsumer = restConsumer;
    }

    public FileGit fazerRequisicao(String diretorioArquivo, String branch) {
        HttpEntity<String> requestEntity = new HttpEntity(gson.toJson(new FileGit()), headers);
        return restConsumer.exchange(this.montarUrlGitLab(diretorioArquivo, branch), HttpMethod.GET, requestEntity,
                FileGit.class).getBody();
    }

    public String fazerRequisicao(String diretorioArquivo) {
        HttpEntity<String> requestEntity = new HttpEntity(gson.toJson(new String()), headers);
        return restConsumer.exchange(diretorioArquivo, HttpMethod.GET, requestEntity,
                String.class).getBody();
    }

    public FileGit gravarArquivo(String diretorioArquivo, FileGit fileGit) {
        HttpEntity<String> requestEntity = new HttpEntity(gson.toJson(fileGit), headers);
        return restConsumer.exchange(urlGitLab + diretorioArquivo, HttpMethod.POST, requestEntity,
                FileGit.class).getBody();
    }

    private String montarUrlGitLab(String diretorioArquivo, String branch) {
        return urlGitLab + diretorioArquivo + "/?ref=" + branch;
    }
}
