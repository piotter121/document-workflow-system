package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;

import java.util.stream.Stream;

public interface ProjectRepository extends MongoRepository<Project, ObjectId> {
    Stream<Project> findByAdministrator(User administrator);
}
