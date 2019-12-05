package br.com.integracao.git.integracaogit.service.impl;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.RepositoryApi;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GitService {
    public String clonarRepositorio() {
        String retorno = "Falhou";
        try {
            GitLabApi gitLabApi = GitLabApi.oauth2Login("https://gitlab.com", "raonybarbosa", "rhb242526".toCharArray());
            //GitLabApi gitLabApi = new GitLabApi("http://gitlab.com/", "XWaAtbd9M93ycAxRCWBH");
            //User currentUser = gitLabApi.getUserApi().getCurrentUser();
            //retorno = currentUser.getUsername();
            List<Project> listaProjetos = gitLabApi.getProjectApi().getOwnedProjects();
            Project project = listaProjetos.get(0);
            //RepositoryApi repositoryApi = gitLabApi.getRepositoryApi();
            //List<Branch> branches = gitLabApi.getRepositoryApi().getBranches(project.getId());
            RepositoryFile file = gitLabApi.getRepositoryFileApi().getFile(project.getId(), "Script.sql", project.getDefaultBranch());
            retorno = file.getFilePath();
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return retorno;
    }
}
