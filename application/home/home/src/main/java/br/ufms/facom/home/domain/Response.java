package br.ufms.facom.home.domain;

public class Response<T> {

    private final T body;

    public Response(T body) {
        this.body = body;
    }

    public T getBody() {
        return body;
    }
}
