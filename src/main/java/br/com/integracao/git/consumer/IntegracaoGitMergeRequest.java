package br.com.integracao.git.consumer;

import br.com.integracao.git.dto.MergeRequestDto;
import br.com.integracao.git.dto.MergeRequestRetornoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${link.git.create.merge.request}", name = "git")
public interface IntegracaoGitMergeRequest {
    @PostMapping(value = "/{identificador}/merge_requests")
    MergeRequestRetornoDto criarMergeRequest(@PathVariable("identificador") int identificador,
                                             @RequestParam("access_token") String accessToken, MergeRequestDto mergeRequestDto);
}
