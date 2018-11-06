package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table("user")
public class User {
    @EqualsAndHashCode.Include
    @ToString.Include
    @PrimaryKey("email")
    @CassandraType(type = DataType.Name.TEXT)
    private String email;

    @Column("password")
    @CassandraType(type = DataType.Name.TEXT)
    private String password;

    @Column("first_name")
    @CassandraType(type = DataType.Name.TEXT)
    private String firstName;

    @Column("last_name")
    @CassandraType(type = DataType.Name.TEXT)
    private String lastName;
}
