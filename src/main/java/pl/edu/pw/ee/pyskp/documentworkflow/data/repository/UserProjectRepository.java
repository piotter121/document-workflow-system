package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.UserProject;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface UserProjectRepository extends CassandraRepository<UserProject> {
    Stream<UserProject> findAllByUserLogin(String userLogin);

    List<UserProject> findAllByUserLoginInAndProjectId(Iterable<String> logins, UUID projectId);

    Optional<UserProject> findUserProjectByUserLoginAndProjectId(String userLogin, UUID projectId);

    void deleteUserProjectByUserLoginAndProjectId(String userLogin, UUID projectId);

    void deleteAllByUserLoginInAndProjectId(Collection<String> logins, UUID projectId);
}
