package br.com.integracao.git.service.impl;

import br.com.integracao.git.exception.NotFoundException;
import br.com.integracao.git.request.ArquivoScript;
import org.apache.commons.io.IOUtils;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GitService {

    GitLabApi gitLabApi;
    LocalDateTime dataHora;
    Project projeto;
    DateTimeFormatter formatter;

    public GitService() {
        gitLabApi = new GitLabApi("https://gitlab.com/", "PB3pRAS1RRFzcnm3ezB3");
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        try {
            projeto = getProjeto("integracaoGit");
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
    }

    public String lerConteudoArquivoGit() {
        String retorno = "Falhou";
        try {
            retorno = retornarConteudoRepositoryFile(projeto.getId(), projeto.getDefaultBranch(), "Script.sql");
        } catch (GitLabApiException | IOException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public String salvarArquivoGit(ArquivoScript arquivoScript) {
        String retorno = "Falhou";
        try {
            dataHora = LocalDateTime.now();
            String agoraFormatado = dataHora.format(formatter);
            Branch branch = this.criarBranch(projeto.getId(), "develop_" + agoraFormatado, projeto.getDefaultBranch());
            RepositoryFile repositoryFile = this.setRepositoryFile("arquivo_" + agoraFormatado + ".sql", "arquivo_" + agoraFormatado + ".sql", arquivoScript.getScripts() + agoraFormatado);
            gitLabApi.getRepositoryFileApi().createFile(projeto.getId(), repositoryFile, branch.getName(), "Arquivo de teste");
            //retorno = repositoryFile.getCommitId();
            retorno = branch.getCommit().getId();
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    private User retornarUsuario() throws GitLabApiException {
        return gitLabApi.getUserApi().getCurrentUser();
    }

    private RepositoryFile setRepositoryFile(String nomeArquivo, String diretorio, String conteudo) {
        RepositoryFile repositoryFile = new RepositoryFile();
        repositoryFile.setFileName(nomeArquivo);
        repositoryFile.setFilePath(diretorio);
        repositoryFile.setContent(conteudo);
        return repositoryFile;
    }

    private RepositoryFile buscarRepositoryFile(Integer idProjeto, String diretorio, String nomeBranch) throws GitLabApiException {
        return gitLabApi.getRepositoryFileApi().getFile(idProjeto, diretorio, nomeBranch);
    }

    private String retornarConteudoRepositoryFile(Integer idProjeto, String nomeBranch, String diretorio) throws GitLabApiException, IOException {
        return IOUtils.toString(gitLabApi.getRepositoryFileApi().getRawFile(idProjeto, nomeBranch, diretorio));
    }

    private Project criarProjeto(String nomeProjeto, String descricaoProjeto) throws GitLabApiException {
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

    private Project getProjeto(String nomeProjeto) throws GitLabApiException {
        return gitLabApi.getProjectApi().getOwnedProjects().stream().filter(x -> x.getName().equals(nomeProjeto)).findFirst().orElseThrow(NotFoundException::new);
    }

    private Branch buscarBranch(Integer idProjeto, String nomeBranch) throws GitLabApiException {
        return gitLabApi.getRepositoryApi().getBranches(idProjeto).stream().filter(x -> x.getName().equals(nomeBranch)).findFirst().orElseThrow(NotFoundException::new);
    }

    private Branch criarBranch(Integer idProjeto, String nomeBranch, String nomeBranchOrigem) throws GitLabApiException {
        return gitLabApi.getRepositoryApi().createBranch(idProjeto, nomeBranch, nomeBranchOrigem);
    }
}
