package br.com.integracao.git.service.impl;

import br.com.integracao.git.component.RestIntegracao;
import br.com.integracao.git.consumer.IntegracaoGit;
import br.com.integracao.git.dto.FileGit;
import br.com.integracao.git.dto.MergeRequestDto;
import br.com.integracao.git.dto.MergeRequestRetornoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class GitLabApiService {

    private final IntegracaoGit integracaoGit;
    private final RestIntegracao restIntegracao;
    @Value("${git.token}")
    String tokenGit;

    public MergeRequestRetornoDto createMergeRequest(MergeRequestDto mergeRequestDto) {
        return integracaoGit.criarMergeRequest(tokenGit, 15661710, mergeRequestDto);
    }

    public FileGit lerArquivo() throws UnsupportedEncodingException {
        return integracaoGit.lerArquivo(tokenGit, 15661710, URLEncoder.encode("DDL/arquivo_02-01-2020-09-48-30.sql", "UTF-8"), "develop");
    }

    public String lerArquivo2() {
        return restIntegracao.fazerRequisicao2();
    }
}
