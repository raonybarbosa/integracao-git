package br.com.integracao.git.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepositorioArquivo {
    CommitRepositorio commitRepositorio;
    List<String> lines;

}
