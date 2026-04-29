package es.daw.extra_peliculas_mvc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Guardar con prefijo ROLE_ (ej: ROLE_USER, ROLE_ADMIN)
     */
    @Column(unique = true, nullable = false, length = 40)
    private String name;

    @Column(length = 100)
    private String description;

    /**
     * Relación inversa opcional (no es estrictamente necesaria, pero útil).
     * No uses ManyToMany: ahora el user tiene un solo role.
     */
    @OneToMany(mappedBy = "role")
    private Set<AppUser> appUsers = new HashSet<>();

    public void addUser(AppUser appUser) {
        appUsers.add(appUser);
        appUser.setRole(this);
    }

    public void removeUser(AppUser appUser) {
        appUsers.remove(appUser);
        appUser.setRole(null);
    }

}
