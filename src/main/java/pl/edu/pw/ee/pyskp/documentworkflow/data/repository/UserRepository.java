package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;

/**
 * Created by piotr on 13.12.16.
 */
public interface UserRepository extends CassandraRepository<User, String> {
}
