package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "user")
public class User {
    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @GeneratedValue
    private Long id;

    @ToString.Include
    @Column(name = "email", nullable = false, unique = true, length = 1000)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToMany(mappedBy = "participants", cascade = CascadeType.MERGE)
    private List<Task> participatedTasks = new ArrayList<>();

    @OneToMany(mappedBy = "administrator")
    private List<Task> administratedTasks = new ArrayList<>();

    @OneToMany(mappedBy = "administrator")
    private List<Project> administratedProjects = new ArrayList<>();

    @Transient
    public String getFullName() {
        return firstName.concat(" ").concat(lastName);
    }
}
