package br.com.integracao.git.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(){
        super("Not Found Exception");
    }
}
