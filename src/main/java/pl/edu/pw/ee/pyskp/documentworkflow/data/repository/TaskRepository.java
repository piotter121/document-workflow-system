package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by piotr on 13.12.16.
 */
public interface TaskRepository extends CassandraRepository<Task> {
    List<Task> findAllByProjectId(UUID projectId);

    List<Task> findAllByProjectIdAndTaskIdNot(UUID projectId, UUID taskId);

    Optional<Task> findTaskByProjectIdAndTaskId(UUID projectId, UUID taskId);

    void deleteAllByProjectId(UUID projectId);

    void deleteTaskByProjectIdAndTaskId(UUID projectId, UUID taskId);
}
