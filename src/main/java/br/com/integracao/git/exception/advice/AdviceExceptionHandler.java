package br.com.integracao.git.exception.advice;

import br.com.integracao.git.exception.NotFoundException;
import br.com.integracao.git.response.Error;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AdviceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity notFoundHandler(NotFoundException ex) {
        return new ResponseEntity(new Error(HttpStatus.NOT_FOUND.toString(), "Categoria não encontrada.", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GitLabApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity gitLabHandler(GitLabApiException ex) {
        return new ResponseEntity(new Error(HttpStatus.NOT_FOUND.toString(), "Erro de Git Lab.", ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
