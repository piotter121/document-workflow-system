package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

/**
 * Created by piotr on 13.12.16.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    int countDistinctByProject(Project project);

    boolean existsByProject_IdAndName(Long projectId, String name);
}
