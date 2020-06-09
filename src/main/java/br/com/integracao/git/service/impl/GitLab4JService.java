package br.com.integracao.git.service.impl;

import br.com.integracao.git.component.RestIntegracao;
import br.com.integracao.git.consumer.IntegracaoGit;
import br.com.integracao.git.dto.MergeRequestDto;
import br.com.integracao.git.exception.NotFoundException;
import br.com.integracao.git.request.ArquivoScript;
import org.apache.commons.io.IOUtils;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitLab4JService {

    GitLabApi gitLabApi;
    LocalDateTime dataHora;
    Project projeto;
    DateTimeFormatter formatter;
    @Autowired
    IntegracaoGit integracaoGit;
    @Autowired
    RestIntegracao restIntegracao;

    public GitLab4JService(@Value("${git.token}") String tokenGit, @Value("${git.url}") String url,
                           @Value("${git.projeto.nome}") String nomeProjeto) throws GitLabApiException {
        gitLabApi = new GitLabApi(url, tokenGit);
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        projeto = getProjeto(nomeProjeto);
    }

    public String lerConteudoArquivoGit() {
        try {
            return retornarConteudoRepositoryFile(projeto.getId(), projeto.getDefaultBranch(), "Script.sql");
        } catch (GitLabApiException | IOException e) {
            throw new NotFoundException();
        }
    }

    public String salvarArquivoGit(ArquivoScript arquivoScript) throws GitLabApiException {
        String retorno = "Falhou";

        dataHora = LocalDateTime.now();
        String agoraFormatado = dataHora.format(formatter);
        Branch branch = this.isValid(projeto.getId(), "develope", projeto.getDefaultBranch());
        RepositoryFile repositoryFile = this.setRepositoryFile("arquivo_" + agoraFormatado + ".sql",
                "arquivo_" + agoraFormatado + ".sql", arquivoScript.getScripts() + agoraFormatado);
        gitLabApi.getRepositoryFileApi().createFile(projeto.getId(), repositoryFile, branch.getName(), "Arquivo de teste");
        retorno = branch.getCommit().getId();
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
        Project project = new Project()
                .withName(nomeProjeto)
                .withDescription(descricaoProjeto)
                .withIssuesEnabled(true)
                .withMergeRequestsEnabled(true)
                .withWikiEnabled(true)
                .withSnippetsEnabled(true)
                .withPublic(true);
        return gitLabApi.getProjectApi().createProject(project);
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

    private Branch isValid(Integer idProject, String nomeBranch, String nomeBranchOrigem) throws GitLabApiException {
        Branch branch;
        try {
            branch = this.buscarBranch(idProject, nomeBranch);
        } catch (NotFoundException e) {
            branch = this.criarBranch(idProject, nomeBranch, nomeBranchOrigem);
        }
        return branch;
    }

    public List<MergeRequestDto> listMergeRequest() {
        List<MergeRequestDto> listaMergeRequestDto = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        try {
            System.out.println("Token               -   " + gitLabApi.getAuthToken());
            System.out.println("-----------------------------------------");
            gitLabApi.getTagsApi().getTags(15661710).forEach(tag -> {
                System.out.println("Nome da tag     -   " + tag.getName());
                System.out.println("Mensagem da tag -   " + tag.getMessage());
            });
            System.out.println("-----------------------------------------");
            gitLabApi.getMergeRequestApi().getMergeRequests(15661710).forEach(mergeRequest -> {
                System.out.println("Id do MR            -   " + mergeRequest.getId());
                System.out.println("Branch fonte        -   " + mergeRequest.getSourceBranch());
                System.out.println("Branch alvo         -   " + mergeRequest.getTargetBranch());
                System.out.println("Titulo do MR        -   " + mergeRequest.getTitle());
                System.out.println("Descrição do MR     -   " + mergeRequest.getDescription());
                System.out.println("Nome do autor do MR -   " + mergeRequest.getAuthor().getName());
                System.out.println("Id do autor do MR   -   " + mergeRequest.getAuthor().getId());
                listaMergeRequestDto.add(modelMapper.map(mergeRequest, MergeRequestDto.class));
            });
        } catch (GitLabApiException e) {
            return listaMergeRequestDto;
        }
        return listaMergeRequestDto;
    }
}
