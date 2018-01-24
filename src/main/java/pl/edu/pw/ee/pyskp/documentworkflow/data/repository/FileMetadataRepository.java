package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
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

    void deleteAllByTaskIdIn(Collection<UUID> tasksIds);

    Optional<FileMetadata> findFileMetadataByTaskIdAndFileId(UUID taskId, UUID fileId);

    void deleteFileMetadataByTaskIdAndFileId(UUID taskId, UUID fileId);

    List<FileMetadata> findAllByTaskId(UUID taskId);

    void deleteAllByTaskId(UUID taskId);
}
