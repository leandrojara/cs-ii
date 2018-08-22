package br.ufms.facom.home.domain.bean;

import br.ufms.facom.home.domain.enums.CodeError;

public class ResponseError {

    private CodeError code;
    private String message;

    public ResponseError() {
    }

    public ResponseError(CodeError code, String message) {
        this.code = code;
        this.message = message;
    }

    public CodeError getCode() {
        return code;
    }

    public void setCode(CodeError code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
