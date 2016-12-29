package pl.edu.pw.ee.pyskp.documentworkflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Project;

import java.util.Optional;

/**
 * Created by piotr on 13.12.16.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findOneByName(String name);
}
