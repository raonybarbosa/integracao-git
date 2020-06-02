package br.com.integracao.git.service.impl;

import br.com.integracao.git.component.RestIntegracao;
import br.com.integracao.git.consumer.IntegracaoGit;
import br.com.integracao.git.dto.FileGit;
import br.com.integracao.git.dto.MergeRequestDto;
import br.com.integracao.git.dto.MergeRequestRetornoDto;
import br.com.integracao.git.exception.NotFoundException;
import br.com.integracao.git.request.ArquivoScript;
import org.apache.commons.io.IOUtils;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitService {

    GitLabApi gitLabApi;
    LocalDateTime dataHora;
    Project projeto;
    DateTimeFormatter formatter;
    @Autowired
    IntegracaoGit integracaoGit;
    @Value("${git.token}")
    String tokenGit;
    @Autowired
    RestIntegracao restIntegracao;

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
            Branch branch = this.isValid(projeto.getId(), "develope", projeto.getDefaultBranch());
            RepositoryFile repositoryFile = this.setRepositoryFile("arquivo_" + agoraFormatado + ".sql", "arquivo_" + agoraFormatado + ".sql", arquivoScript.getScripts() + agoraFormatado);
            gitLabApi.getRepositoryFileApi().createFile(projeto.getId(), repositoryFile, branch.getName(), "Arquivo de teste");
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
            for (Tag tag : gitLabApi.getTagsApi().getTags(15661710)) {
                System.out.println("Nome da tag     -   " + tag.getName());
                System.out.println("Mensagem da tag -   " + tag.getMessage());
            }
            System.out.println("-----------------------------------------");
            for (MergeRequest mr :
                    gitLabApi.getMergeRequestApi().getMergeRequests(15661710)) {
                System.out.println("Id do MR            -   " + mr.getId());
                System.out.println("Branch fonte        -   " + mr.getSourceBranch());
                System.out.println("Branch alvo         -   " + mr.getTargetBranch());
                System.out.println("Titulo do MR        -   " + mr.getTitle());
                System.out.println("Descrição do MR     -   " + mr.getDescription());
                System.out.println("Nome do autor do MR -   " + mr.getAuthor().getName());
                System.out.println("Id do autor do MR   -   " + mr.getAuthor().getId());
                listaMergeRequestDto.add(modelMapper.map(mr, MergeRequestDto.class));
            }
        } catch (GitLabApiException e) {
            return listaMergeRequestDto;
        }
        return listaMergeRequestDto;
    }

    public MergeRequestRetornoDto createMergeRequest(MergeRequestDto mergeRequestDto) {
        return integracaoGit.criarMergeRequest(tokenGit, 15661710, mergeRequestDto);
    }

    public FileGit lerArquivo() {
        return restIntegracao.fazerRequisicao2();
    }

    public FileGit lerArquivo2() throws UnsupportedEncodingException {
        return integracaoGit.lerArquivo(tokenGit, 15661710, URLEncoder.encode("DDL/arquivo_02-01-2020-09-48-30.sql", "UTF-8"), "develop");
    }
}
