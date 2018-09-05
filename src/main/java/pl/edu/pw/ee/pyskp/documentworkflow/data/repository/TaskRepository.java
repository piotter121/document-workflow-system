package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

import java.util.List;

/**
 * Created by piotr on 13.12.16.
 */
public interface TaskRepository extends MongoRepository<Task, ObjectId> {
    List<Task> findByProject(Project project);

    List<Task> findByParticipants_EmailContainingOrAdministrator_Email(String participantEmail, String adminEmail);

    List<Task> findByProject_Id(ObjectId projectId);
}
