package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created by piotr on 06.01.17.
 */
public interface FileMetadataRepository extends CassandraRepository<FileMetadata, MapId> {
    Stream<FileMetadata> findByTaskIdIn(Collection<UUID> taskIds);

    void deleteByTaskIdIn(Collection<UUID> tasksIds);

    Optional<FileMetadata> findOneByTaskIdAndFileId(UUID taskId, UUID fileId);

    @Query("update file_metadata set marked_to_confirm = true where task_id = :taskId and file_id = :fileId")
    void updateMarkToConfirmTrueByTaskIdAndFileId(@Param("taskId") UUID taskId, @Param("fileId") UUID fileId);

    @Query("update file_metadata set confirmed = true where task_id = :taskId and file_id = :fileId")
    void updateConfirmedTrueByTaskIdAndFileId(@Param("taskId") UUID taskId, @Param("fileId") UUID fileId);

    @Query("delete from file_metadata where task_id = :task_id and file_id = :file_id")
    void deleteFileMetadataByTaskIdAndFileId(@Param("task_id") UUID taskId, @Param("file_id") UUID fileId);

    Stream<FileMetadata> findByTaskId(UUID taskId);

    @Query("delete from file_metadata where task_id = :task_id")
    void deleteAllByTaskId(@Param("task_id") UUID taskId);
}
