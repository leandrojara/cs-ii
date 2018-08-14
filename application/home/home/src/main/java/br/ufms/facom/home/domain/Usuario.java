package br.ufms.facom.home.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull(message = "O nome do usuário não foi informado!")
    @NotEmpty(message = "O nome do usuário não foi informado!")
    @Column(name = "nome")
    protected String nome;

    @NotNull(message = "O telefone do usuário não foi informado!")
    @NotEmpty(message = "O telefone do usuário não foi informado!")
    @Column(name = "telefone")
    protected String telefone;

    @NotNull(message = "O e-mail do usuário não foi informado!")
    @NotEmpty(message = "O e-mail do usuário não foi informado!")
    @Column(name = "email")
    protected String email;

    @NotNull(message = "A senha do usuário não foi informada!")
    @NotEmpty(message = "A senha do usuário não foi informada!")
    @Column(name = "senha")
    protected String senha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
