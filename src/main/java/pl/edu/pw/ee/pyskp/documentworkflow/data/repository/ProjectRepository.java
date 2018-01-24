package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by piotr on 13.12.16.
 */
public interface ProjectRepository extends CassandraRepository<Project> {
    Optional<Project> findOneById(UUID id);
    void deleteProjectById(UUID id);
}
