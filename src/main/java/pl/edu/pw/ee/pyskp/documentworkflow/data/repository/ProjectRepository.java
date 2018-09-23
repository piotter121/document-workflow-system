package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select p from Project p left join fetch p.tasks")
    Optional<Project> findOneByIdFetchedTasks(Long id);

    @Query("select p " +
            "from Project p " +
            "join p.administrator admin " +
            "left join p.tasks tasks " +
            "left join tasks.administrator taskAdmin " +
            "left join tasks.participants taskParticipants " +
            "where admin.email = :email " +
            "or taskAdmin.email = :email " +
            "or taskParticipants.email = :email ")
    Stream<Project> findParticipatedProjects(@Param("email") String email);

    @Query("select case when (count(distinct p.id) > 0) then true else false end " +
            "from Project p " +
            "join p.administrator padmin " +
            "left join p.tasks ptasks " +
            "left join ptasks.administrator ptadmin " +
            "left join ptasks.participants ptparticipants " +
            "where p.id = :projectId " +
            "and (padmin.email = :email " +
            "or ptadmin.email = :email " +
            "or ptparticipants.email = :email)")
    boolean hasAccessToProject(@Param("email") String userEmail, @Param("projectId") Long projectId);

    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
    @Query("select p.name from Project p where p.id = :id")
    Optional<String> findNameById(@Param("id") Long projectId);
}
