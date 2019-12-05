package br.com.integracao.git.integracaogit.service.impl;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.Pager;
import org.gitlab4j.api.models.Project;
import org.springframework.stereotype.Service;

@Service
public class GitService {
    public String clonarRepositorio() {
        try {
            GitLabApi gitLabApi = new GitLabApi("http://gitlab.raonybarbosa.com/", "XWaAtbd9M93ycAxRCWBH");
            // Get a Pager instance so we can load all the projects into a single list, 10 items at a time:
//            Pager<Project> projectPager = gitLabApi.getProjectApi().getProjects(1);
//            List<Project> allProjects = projectPager.all();
//            allProjects.forEach(System.out::println);
            // Get a Pager instance that will page through the projects with 10 projects per page
            Pager<Project> projectPager = gitLabApi.getProjectApi().getProjects(10);

// Iterate through the pages and print out the name and description
            while (projectPager.hasNext()) {
                for (Project project : projectPager.next()) {
                    System.out.println(project.getName() + " -: " + project.getDescription());
                }
            }
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
//        String repoUrl = "https://gitlab.com/lipe.nscm/horus.git";
//        String cloneDirectoryPath = "/GitLab/teste git/"; // Ex.in windows c:\\gitProjects\SpringBootMongoDbCRUD\
//        try {
//            System.out.println("Cloning " + repoUrl + " into " + repoUrl);
//            Git.cloneRepository()
//                    .setURI(repoUrl)
//                    .setDirectory(Paths.get(cloneDirectoryPath).toFile())
//                    .call();
//            return "Completed Cloning";
//        } catch (GitAPIException e) {
//            e.printStackTrace();
//            return "Exception occurred while cloning repo";
//        }

        return "Baixou";
    }
}
