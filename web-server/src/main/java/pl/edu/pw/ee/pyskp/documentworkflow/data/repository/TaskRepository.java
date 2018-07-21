package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by piotr on 13.12.16.
 */
public interface TaskRepository extends CassandraRepository<Task> {
    List<Task> findAllByProjectId(UUID projectId);

    Optional<Task> findTaskByProjectIdAndTaskId(UUID projectId, UUID taskId);

    @Query("delete from task where project_id = :project_id")
    void deleteAllByProjectId(@Param("project_id") UUID projectId);

    @Query("delete from task where project_id = :project_id and task_id = :task_id")
    void deleteTaskByProjectIdAndTaskId(@Param("project_id") UUID projectId, @Param("task_id") UUID taskId);
}
