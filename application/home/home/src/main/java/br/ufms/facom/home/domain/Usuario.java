package br.ufms.facom.home.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull(message = "O nome do usuário não foi informado!")
    @NotEmpty(message = "O nome do usuário não foi informado!")
    @Column(name = "nome")
    protected String nome;

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

    @Column(name = "bloqueado")
    protected boolean bloqueado;

    @Transient
    private List<GrantedAuthority> grantList;

    public Usuario() {
    }

    public Usuario(String nome, String senha, List<GrantedAuthority> grantList) {
        this.nome = nome;
        this.senha = senha;
        this.grantList = grantList;
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantList;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !bloqueado;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !bloqueado;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !bloqueado;
    }

    @Override
    public boolean isEnabled() {
        return !bloqueado;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public List<GrantedAuthority> getGrantList() {
        return grantList;
    }

    public void setGrantList(List<GrantedAuthority> grantList) {
        this.grantList = grantList;
    }
}
