package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.UserProject;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface UserProjectRepository extends CassandraRepository<UserProject, MapId> {
    Stream<UserProject> findAllByUserEmail(String userEmail);

    List<UserProject> findAllByUserEmailInAndProjectId(Collection<String> emails, UUID projectId);

    Optional<UserProject> findByUserEmailAndProjectId(String userEmail, UUID projectId);

    @Query("delete from user_project where user_email = :user_email and project_id = :project_id")
    void deleteUserProjectByUserEmailAndProjectId(@Param("user_email") String userEmail,
                                                  @Param("project_id") UUID projectId);

    @Query("delete from user_project where user_email in (:emails) and project_id = :project_id")
    void deleteAllByUserEmailInAndProjectId(@Param("emails") Collection<String> emails,
                                            @Param("project_id") UUID projectId);
}
