package br.com.integracao.git.consumer;

import br.com.integracao.git.dto.FileGit;
import br.com.integracao.git.dto.MergeRequestDto;
import br.com.integracao.git.dto.MergeRequestRetornoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "${link.git.create.merge.request}", name = "git")
public interface IntegracaoGit {
    @PostMapping(value = "/{identificador}/merge_requests")
    MergeRequestRetornoDto criarMergeRequest(@RequestHeader("private-token") String token, @PathVariable("identificador") int identificador, MergeRequestDto mergeRequestDto);

    @GetMapping(value = "/{identificador}/repository/files/{arquivo}?ref={branch}")
    FileGit lerArquivo(@RequestHeader("private-token") String token, @PathVariable("identificador") int identificador,
                       @PathVariable("arquivo") String arquivo, @PathVariable("branch") String branch);
}
