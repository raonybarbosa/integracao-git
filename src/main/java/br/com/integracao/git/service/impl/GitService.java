package br.com.integracao.git.service.impl;

import br.com.integracao.git.response.RepositorioArquivo;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.RepositoryApi;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GitService {
    @Autowired
    RestIntegracao restIntegracao;

    public String clonarRepositorio() {
        String retorno = "Falhou";
        try {
            GitLabApi gitLabApi = new GitLabApi("https://gitlab.com/", "PB3pRAS1RRFzcnm3ezB3");
            User currentUser = gitLabApi.getUserApi().getCurrentUser();
            retorno = currentUser.getUsername();
            List<Project> listaProjetos = gitLabApi.getProjectApi().getOwnedProjects();
            Project project = listaProjetos.get(0);
            RepositoryApi repositoryApi = gitLabApi.getRepositoryApi();
            List<Branch> branches = gitLabApi.getRepositoryApi().getBranches(project.getId());
            RepositorioArquivo repositorioArquivo = restIntegracao.fazerRequisicao();
            //https://gitlab.com/api/v4/projects/15661710/repository/files/Script.sql/blame?ref=master
            RepositoryFile file = gitLabApi.getRepositoryFileApi().getFile(project.getId(), "Script.sql", project.getDefaultBranch());
            retorno = retorno + " " + file.getFilePath();
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return retorno;
    }
}
