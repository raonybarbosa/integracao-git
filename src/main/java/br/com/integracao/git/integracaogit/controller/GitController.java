package br.com.integracao.git.integracaogit.controller;

import br.com.integracao.git.integracaogit.response.Response;
import br.com.integracao.git.integracaogit.service.impl.GitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/git")
public class GitController {
    @Autowired
    GitService gitService;

    @GetMapping
    public ResponseEntity<Response> returnListAllUsers() {
        return ResponseEntity.ok(new Response(gitService.clonarRepositorio()));
    }
}
