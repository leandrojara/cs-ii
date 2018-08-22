package br.ufms.facom.home.domain.enums;

public enum CodeError {

    USUARIO_EXISTENTE(1);

    private final int code;

    CodeError(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
