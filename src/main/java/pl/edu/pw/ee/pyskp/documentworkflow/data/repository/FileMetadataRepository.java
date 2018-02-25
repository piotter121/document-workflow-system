package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by piotr on 06.01.17.
 */
public interface FileMetadataRepository extends CassandraRepository<FileMetadata> {
    List<FileMetadata> findAllByTaskIdIn(Collection<UUID> tasksIds);

    @Query("delete from file_metadata where task_id in (:tasks)")
    void deleteAllByTaskIdIn(@Param("tasks") Collection<UUID> tasksIds);

    Optional<FileMetadata> findOneByTaskIdAndFileId(UUID taskId, UUID fileId);

    @Query("delete from file_metadata where task_id = :task_id and file_id = :file_id")
    void deleteFileMetadataByTaskIdAndFileId(@Param("task_id") UUID taskId, @Param("file_id") UUID fileId);

    List<FileMetadata> findAllByTaskId(UUID taskId);

    @Query("delete from file_metadata where task_id = :task_id")
    void deleteAllByTaskId(@Param("task_id") UUID taskId);
}
