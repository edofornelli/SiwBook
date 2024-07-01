package it.uniroma3.siw.siwbook.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.cdi.Eager;

import java.util.Objects;

@Entity
public class Credentials {

    public static final String GENERIC_ROLE = "GENERIC";
    public static final String ADMIN_ROLE = "ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Column(nullable = false,unique = true)
    private String username;

    @NotBlank
    private String password;

    //nunpuoi mette @NotBlank perche' e' un campo che non e' visibile all'utente e ritorna vuoto dal form
    private String role;


    @OneToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    public User getUser() {
        return user;
    }

    public boolean isAdmin() {
        return this.role.equals(ADMIN_ROLE);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return id == that.id && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }

}
