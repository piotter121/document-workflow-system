package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(of = "email")
@Table("user")
public class User {
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
