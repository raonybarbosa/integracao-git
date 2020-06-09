package br.com.integracao.git.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileGit {
    private String file_name;
    private String file_path;
    private int size;
    private String encoding;
    private String content_sha256;
    private String ref;
    private String blob_id;
    private String commit_id;
    private String last_commit_id;
    private String content;
}
