package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;

import java.util.UUID;

public interface ProjectRepository extends CassandraRepository<Project, UUID> {
}
