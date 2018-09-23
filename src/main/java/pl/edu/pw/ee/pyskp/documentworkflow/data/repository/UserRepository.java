package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;

import java.util.Optional;

/**
 * Created by piotr on 13.12.16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);

    boolean existsByEmail(String email);

    @SuppressWarnings("SpringDataRepositoryMethodParametersInspection")
    int countDistinctByAdministratedProjectsContainsOrAdministratedTasks_ProjectOrParticipatedTasks_Project(
            Project project1, Project project2, Project project3);

    int countDistinctByAdministratedTasksContainsOrParticipatedTasksContains(Task administratedTask, Task participatedTask);
}
