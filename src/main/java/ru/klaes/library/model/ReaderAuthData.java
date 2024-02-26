package ru.klaes.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reader_auth_data")
@Data
@NoArgsConstructor
public class ReaderAuthData {

    @Id
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToOne
    @MapsId
    private Reader reader;

    @ManyToMany (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role){
        this.roles.add(role);
        role.getUsers().add(this);
    }
    public void removeRole(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}
