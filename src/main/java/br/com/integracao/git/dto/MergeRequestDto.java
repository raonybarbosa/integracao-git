package br.com.integracao.git.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MergeRequestDto {
    private Integer id;
    @JsonProperty(value = "source_branch")
    private String sourceBranch;
    @JsonProperty(value = "target_branch")
    private String targetBranch;
    private String title;
}
