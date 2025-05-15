package br.com.projeto.apigerenciamentodeestoque.model.User;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "roles")
@Entity
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long roleId;

    private String roleName;

    public enum Roles{
        ADMIN(1L),
        STOCKER(2L),
        COMMON(3L);

        Long roleID;

        Roles(Long roleId) {
            this.roleID = roleId;
        }
    }
}
