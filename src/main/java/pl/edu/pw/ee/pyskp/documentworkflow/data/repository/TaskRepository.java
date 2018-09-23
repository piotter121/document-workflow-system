package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

import java.util.Optional;

/**
 * Created by piotr on 13.12.16.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    int countDistinctByProject(Project project);

    @Query("select t from Task t left join fetch t.participants where t.id = :taskId")
    Optional<Task> findOneWithFetchedParticipants(@Param("taskId") Long taskId);

    boolean existsByProject_IdAndName(Long projectId, String name);
}
