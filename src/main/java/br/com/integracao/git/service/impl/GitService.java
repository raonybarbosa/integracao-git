package br.com.integracao.git.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.RepositoryApi;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class GitService {
    @Autowired
    RestIntegracao restIntegracao;

    public String clonarRepositorio() {
        String retorno = "Falhou";
        try {
            GitLabApi gitLabApi = new GitLabApi("https://gitlab.com/", "PB3pRAS1RRFzcnm3ezB3");
            List<Project> listaProjetos = gitLabApi.getProjectApi().getOwnedProjects();
            Project project = listaProjetos.get(0);
//            RepositoryApi repositoryApi = gitLabApi.getRepositoryApi();
//            List<Branch> branches = gitLabApi.getRepositoryApi().getBranches(project.getId());

//            https://gitlab.com/api/v4/projects/15661710/repository/files/Script.sql/blame?ref=master
            RepositoryFile file = gitLabApi.getRepositoryFileApi().getFile(project.getId(), "Script.sql", project.getDefaultBranch());
            InputStream jsonMap2 = gitLabApi.getRepositoryFileApi().getRawFile(project.getId(), project.getDefaultBranch(), "Script.sql");
            ObjectMapper mapper = new ObjectMapper();
            retorno = mapper.readValue(jsonMap2, String.class);
//            retorno = retorno + " " + file.getFilePath();
        } catch (GitLabApiException | IOException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    private User retornarUsuario(GitLabApi gitLabApi) throws GitLabApiException {
        return gitLabApi.getUserApi().getCurrentUser();
    }

    private Project criarProjetoGit(GitLabApi gitLabApi, String nomeProjeto, String descricaoProjeto) throws GitLabApiException {
        Project projeto = new Project()
                .withName(nomeProjeto)
                .withDescription(descricaoProjeto)
                .withIssuesEnabled(true)
                .withMergeRequestsEnabled(true)
                .withWikiEnabled(true)
                .withSnippetsEnabled(true)
                .withPublic(true);
        return gitLabApi.getProjectApi().createProject(projeto);
    }
}
