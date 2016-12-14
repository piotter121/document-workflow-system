package pl.edu.pw.ee.pyskp.documentworkflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Project;

import java.util.List;

/**
 * Created by piotr on 13.12.16.
 */
public interface ProjectsRepository extends JpaRepository<Project, Long> {
    List<Project> findByName(String name);
}
