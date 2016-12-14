package pl.edu.pw.ee.pyskp.documentworkflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Task;

import java.util.List;

/**
 * Created by piotr on 13.12.16.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByName(String name);
}
