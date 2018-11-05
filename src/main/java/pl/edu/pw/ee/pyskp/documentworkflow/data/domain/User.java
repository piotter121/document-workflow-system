package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Document
public class User {
    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    private ObjectId id;

    @EqualsAndHashCode.Include
    @ToString.Include
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
