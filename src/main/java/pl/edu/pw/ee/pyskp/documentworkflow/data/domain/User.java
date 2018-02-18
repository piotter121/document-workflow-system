package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.Indexed;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(of = "login")
@Table
public class User {
    @PrimaryKey
    private String login;

    private String password;

    @Indexed
    private String email;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;
}
