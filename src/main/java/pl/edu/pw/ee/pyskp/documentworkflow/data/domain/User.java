package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.mapping.Indexed;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Created by piotr on 11.12.16.
 */
@Table
@Data
@EqualsAndHashCode(of = "login")
public class User {
    @PrimaryKey
    private String login;

    private String password;

    @Indexed
    private String email;

    private String firstName;

    private String lastName;
}
