package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;

import java.util.Optional;

/**
 * Created by piotr on 13.12.16.
 */
public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findOneByEmail(String email);
}
