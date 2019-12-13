package br.com.integracao.git.controller;

import br.com.integracao.git.request.ArquivoScript;
import br.com.integracao.git.response.Response;
import br.com.integracao.git.service.impl.GitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/git")
public class GitController {
    @Autowired
    GitService gitService;

    @GetMapping
    public ResponseEntity<Response> returnListAllUsers() {
        return ResponseEntity.ok(new Response(gitService.lerConteudoArquivoGit()));
    }

    @PostMapping
    public ResponseEntity<Response> salvarArquivoGit(@RequestBody Response response) {
        ModelMapper modelMapper = new ModelMapper();
        ArquivoScript arquivoScript = modelMapper.map(response.getData(), ArquivoScript.class);
        return ResponseEntity.ok(new Response(gitService.salvarArquivoGit(arquivoScript)));
    }
}
