package br.com.integracao.git.consumer;

import br.com.integracao.git.dto.MergeRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${link.git.create.merge.request}", name = "git")
public interface IntegracaoGitMergeRequest {
    @PostMapping("/{identificador}/merge_requests")
    void criarMergeRequest(@PathVariable("identificador") String identificador, MergeRequestDto mergeRequestDto);
}
