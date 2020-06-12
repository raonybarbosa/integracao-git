package br.com.integracao.git.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileGit {

    @JsonProperty(value="file_name")
    @SerializedName("file_name")
    private String fileName;
    @JsonProperty(value="file_path")
    @SerializedName("file_path")
    private String filePath;
    private int size;
    private String encoding;
    @JsonProperty(value="content_sha256")
    @SerializedName("content_sha256")
    private String contentSha256;
    private String ref;
    @JsonProperty(value="blob_id")
    @SerializedName("blob_id")
    private String blobId;
    @JsonProperty(value="commit_id")
    @SerializedName("commit_id")
    private String commitId;
    @JsonProperty(value="last_commit_id")
    @SerializedName("last_commit_id")
    private String lastCommitId;
    private String content;
    private String branch;
    @JsonProperty(value="commit_message")
    @SerializedName("commit_message")
    private String commitMessage;
}