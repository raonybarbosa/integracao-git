package br.com.integracao.git.consumer;

import br.com.integracao.git.dto.FileGit;
import br.com.integracao.git.dto.MergeRequestDto;
import br.com.integracao.git.dto.MergeRequestRetornoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${git.url.api}", name = "git")
public interface IntegracaoGit {
    @PostMapping(value = "/{identificador}/merge_requests", headers = "private-token=${git.token}")
    MergeRequestRetornoDto criarMergeRequest(@PathVariable("identificador") int identificador,
                                             MergeRequestDto mergeRequestDto);

    @GetMapping(value = "/{identificador}/repository/files/{arquivo}?ref={branch}", headers = "private-token=${git.token}")
    FileGit lerArquivo(@PathVariable("identificador") int identificador,
                       @PathVariable("arquivo") String arquivo,
                       @PathVariable("branch") String branch);

    @PostMapping(value = "/{identificador}/repository/files/{nomeArquivo}", headers = "private-token=${git.token}")
    FileGit gravarArquivo(@PathVariable("identificador") int identificador,
                          @PathVariable("nomeArquivo") String nomeArquivo,
                          @RequestBody FileGit fileGit);
}