package pl.edu.pw.ee.pyskp.documentworkflow.domain;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import org.hibernate.annotations.Parameter;

/**
 * Created by piotr on 11.12.16.
 */
@Entity
public class UserDetails {
    @Id
    @GenericGenerator(name = "userGen", strategy = "foreign",
    parameters = @Parameter(name = "property", value = "user"))
    @GeneratedValue(generator = "userGen")
    private long id;

    private String name;
    private String surname;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
