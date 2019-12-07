package br.com.integracao.git.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommitRepositorio {
    String id;
    List<String> parent_ids;
}
