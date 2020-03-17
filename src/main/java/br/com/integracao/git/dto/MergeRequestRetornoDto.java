package br.com.integracao.git.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MergeRequestRetornoDto {

    private Integer id;
    private Integer iid;
    private Integer project_id;
    private String title;
    private String description;
    private String state;
    private String created_at;
    private String updated_at;
    private String target_branch;
    private String source_branch;
    private List<String> message;
}
