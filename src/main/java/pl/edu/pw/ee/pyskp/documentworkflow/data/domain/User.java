package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(of = "email")
@Document
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @Transient
    public String getFullName() {
        return firstName.concat(" ").concat(lastName);
    }
}
