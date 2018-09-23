package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.ContentType;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

import java.util.List;
import java.util.Optional;

/**
 * Created by piotr on 06.01.17.
 */
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    int countDistinctByTask_Project(Project project);

    Optional<FileMetadata> findFirstByTask_ProjectOrderByLatestVersion_SaveDateDesc(Project project);

    Optional<FileMetadata> findFirstByTaskOrderByLatestVersion_SaveDateDesc(Task task);

    @Query("select distinct f.id from FileMetadata f join f.task t join t.project p where p.id = :projectId")
    List<Long> findIdByTask_Project_Id(@Param("projectId") Long projectId);

    int countDistinctByTask(Task task);

    @Query("select distinct f.id from FileMetadata f join f.task t where t.id = :taskId")
    List<Long> findIdByTask_Id(@Param("taskId") Long taskId);

    @Modifying
    @Query("update FileMetadata f set f.markedToConfirm = true where f.id = :fileId")
    int markToConfirm(@Param("fileId") Long fileId);

    boolean existsByIdAndContentType(Long id, ContentType contentType);

    @Modifying
    @Query("update FileMetadata f set f.confirmed = true where f.id = :fileId")
    int setConfirmedTrue(@Param("fileId") Long fileId);
}
