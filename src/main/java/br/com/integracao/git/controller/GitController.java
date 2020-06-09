package br.com.integracao.git.controller;

import br.com.integracao.git.dto.FileGit;
import br.com.integracao.git.dto.MergeRequestDto;
import br.com.integracao.git.dto.MergeRequestRetornoDto;
import br.com.integracao.git.service.impl.GitLab4JService;
import br.com.integracao.git.service.impl.GitLabApiRestService;
import br.com.integracao.git.service.impl.GitLabApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/git")
@RequiredArgsConstructor
public class GitController {

    private final GitLab4JService gitLab4JService;
    private final GitLabApiService gitLabApiService;
    private final GitLabApiRestService gitLabApiRestService;

    @GetMapping("/feign")
    public ResponseEntity<FileGit> lerArquivo() throws UnsupportedEncodingException {
        return ResponseEntity.ok(gitLabApiService.lerArquivo());
    }

    @GetMapping("/rest")
    public ResponseEntity<FileGit> lerArquivo2() throws UnsupportedEncodingException {
        return ResponseEntity.ok(gitLabApiRestService.lerArquivo2());
    }

    @PostMapping
    public ResponseEntity<MergeRequestRetornoDto> returnMergeRequest(@RequestBody MergeRequestDto mergeRequestDto) {
        return ResponseEntity.ok(gitLabApiService.createMergeRequest(mergeRequestDto));
    }
}
