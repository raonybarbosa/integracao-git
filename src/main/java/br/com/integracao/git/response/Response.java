package br.com.integracao.git.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {

    private T data;

    public Response() {

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Response(T data) {
        this.data = data;
    }
}