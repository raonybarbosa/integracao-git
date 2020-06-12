package br.com.integracao.git.service.impl;

import br.com.integracao.git.consumer.IntegracaoGit;
import br.com.integracao.git.dto.FileGit;
import br.com.integracao.git.dto.MergeRequestDto;
import br.com.integracao.git.dto.MergeRequestRetornoDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class GitLabApiService {

    private final IntegracaoGit integracaoGit;
    @Value("${git.projeto.id}")
    int idProjeto;

    public MergeRequestRetornoDto createMergeRequest(MergeRequestDto mergeRequestDto) {
        return integracaoGit.criarMergeRequest(idProjeto, mergeRequestDto);
    }

    public FileGit lerArquivo() throws UnsupportedEncodingException {
        return integracaoGit.lerArquivo(idProjeto, URLEncoder.encode("DDL/arquivo_02-01-2020-09-48-30.sql", "UTF-8"), "develop");
    }

    public FileGit gravarArquivo(FileGit fileGit) throws UnsupportedEncodingException {
        try {
            return integracaoGit.lerArquivo(idProjeto, URLEncoder.encode(fileGit.getFileName(), "UTF-8"), fileGit.getBranch());
        } catch (FeignException.NotFound e) {
            return integracaoGit.gravarArquivo(idProjeto, URLEncoder.encode(fileGit.getFileName(), "UTF-8"), fileGit);
        }
    }
}