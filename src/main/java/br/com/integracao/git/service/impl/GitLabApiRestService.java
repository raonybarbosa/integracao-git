package br.com.integracao.git.service.impl;

import br.com.integracao.git.component.RestIntegracao;
import br.com.integracao.git.dto.FileGit;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class GitLabApiRestService {

    private final RestIntegracao restIntegracao;

    public String lerArquivo() {
        return restIntegracao.fazerRequisicao("https://gitlab.com/raonybarbosa/integracaogit/-/raw/develop/DDL/arquivo_02-01-2020-09-48-30.sql?inline=false");
    }

    public FileGit lerArquivo2() throws UnsupportedEncodingException {
        return restIntegracao.fazerRequisicao(URLEncoder.encode("DDL/arquivo_02-01-2020-09-48-30.sql", "UTF-8"), "develop");
    }

    public FileGit gravarArquivo(FileGit fileGit) throws UnsupportedEncodingException {
        try {
            return restIntegracao.fazerRequisicao(URLEncoder.encode(fileGit.getFileName(), "UTF-8"), fileGit.getBranch());
        } catch (HttpClientErrorException.NotFound e) {
            return restIntegracao.gravarArquivo(URLEncoder.encode(fileGit.getFileName(), "UTF-8"), fileGit);
        }
    }
}
