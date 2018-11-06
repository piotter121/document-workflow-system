package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "project")
public class Project {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private Timestamp creationDate;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "administrator_id", nullable = false)
    private User administrator;

    @ToString.Exclude
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Task> tasks = new ArrayList<>();
}