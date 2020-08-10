package br.com.integracao.git.controller;

import br.com.integracao.git.dto.FileGit;
import br.com.integracao.git.dto.MergeRequestDto;
import br.com.integracao.git.service.impl.GitLabApiRestService;
import br.com.integracao.git.service.impl.GitLabApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/git")
@RequiredArgsConstructor
public class GitController {

    private final GitLabApiService gitLabApiService;
    private final GitLabApiRestService gitLabApiRestService;

    @GetMapping("/feign")
    public ResponseEntity lerArquivoFeign() throws UnsupportedEncodingException {
        return ResponseEntity.ok(gitLabApiService.lerArquivo());
    }

    @GetMapping("/rest")
    public ResponseEntity lerArquivoRest() throws UnsupportedEncodingException {
        return ResponseEntity.ok(gitLabApiRestService.lerArquivo2());
    }

    @PostMapping("/feign/mergerequest")
    public ResponseEntity returnMergeRequest(@RequestBody MergeRequestDto mergeRequestDto) {
        return ResponseEntity.ok(gitLabApiService.createMergeRequest(mergeRequestDto));
    }

    @PostMapping("/feign/gravararquivo")
    public ResponseEntity gravarArquivoFeign(@RequestBody @Valid FileGit fileGit) throws UnsupportedEncodingException {
        return ResponseEntity.ok(gitLabApiService.gravarArquivo(fileGit));
    }

    @PostMapping("/rest/gravararquivo")
    public ResponseEntity gravarArquivoRest(@RequestBody @Valid FileGit fileGit) throws UnsupportedEncodingException {
        return ResponseEntity.ok(gitLabApiRestService.gravarArquivo(fileGit));
    }
}