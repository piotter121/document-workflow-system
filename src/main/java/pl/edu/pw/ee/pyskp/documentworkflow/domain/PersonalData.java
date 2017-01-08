package pl.edu.pw.ee.pyskp.documentworkflow.domain;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import org.hibernate.annotations.Parameter;

/**
 * Created by piotr on 11.12.16.
 */
@Entity
public class PersonalData {
    @Id
    @GenericGenerator(name = "userGen", strategy = "foreign",
    parameters = @Parameter(name = "property", value = "user"))
    @GeneratedValue(generator = "userGen")
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private User user;

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
