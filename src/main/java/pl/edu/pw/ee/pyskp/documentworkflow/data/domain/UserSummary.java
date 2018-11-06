package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@UserDefinedType("user_summary")
public class UserSummary {
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column("email")
    @CassandraType(type = DataType.Name.TEXT)
    private String email;

    @SerializedName("first_name")
    @Column("first_name")
    @CassandraType(type = DataType.Name.TEXT)
    private String firstName;

    @SerializedName("last_name")
    @Column("last_name")
    @CassandraType(type = DataType.Name.TEXT)
    private String lastName;

    public UserSummary(User user) {
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
    }
}
