package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("delete from user_project where user_login = :user_login and project_id = :project_id")
    void deleteUserProjectByUserLoginAndProjectId(@Param("user_login") String userLogin,
                                                  @Param("project_id") UUID projectId);

    @Query("delete from user_project where user_login in (:logins) and project_id = :project_id")
    void deleteAllByUserLoginInAndProjectId(@Param("logins") Collection<String> logins,
                                            @Param("project_id") UUID projectId);
}
