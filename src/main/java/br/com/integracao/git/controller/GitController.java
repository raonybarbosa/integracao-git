package br.com.integracao.git.controller;

import br.com.integracao.git.dto.MergeRequestDto;
import br.com.integracao.git.response.Response;
import br.com.integracao.git.service.impl.GitLab4JService;
import br.com.integracao.git.service.impl.GitLabApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/git")
@RequiredArgsConstructor
public class GitController {

    private final GitLabApiService gitLabApiService;
    private final GitLab4JService gitLab4JService;

    //@GetMapping
    //public ResponseEntity<Response> returnListAllUsers() {
    //    return ResponseEntity.ok(new Response(gitService.lerConteudoArquivoGit()));
    //}

    @GetMapping
    public ResponseEntity<Response> returnMergeRequest() throws UnsupportedEncodingException {
        //return ResponseEntity.ok(new Response(gitLabApiService.lerArquivo2()));
        return ResponseEntity.ok(new Response(gitLabApiService.lerArquivo()));
    }

    @PostMapping
    public ResponseEntity<Response> returnMergeRequest(@RequestBody MergeRequestDto mergeRequestDto) {
        return ResponseEntity.ok(new Response(gitLabApiService.createMergeRequest(mergeRequestDto)));
    }

    /*@PostMapping
    public ResponseEntity<Response> salvarArquivoGit(@RequestBody Response response) {
        ModelMapper modelMapper = new ModelMapper();
        ArquivoScript arquivoScript = modelMapper.map(response.getData(), ArquivoScript.class);
        return ResponseEntity.ok(new Response(gitService.salvarArquivoGit(arquivoScript)));
    }*/
}
