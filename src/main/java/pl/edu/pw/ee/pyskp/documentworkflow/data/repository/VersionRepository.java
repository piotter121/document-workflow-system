package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.*;

/**
 * Created by piotr on 06.01.17.
 */
public interface VersionRepository extends CassandraRepository<Version> {
    @Query("delete from version where file_id in (:files)")
    void deleteAllByFileIdIn(@Param("files") Collection<UUID> filesIds);

    @Query("delete from version where file_id = :file_id")
    void deleteAllByFileId(@Param("file_id") UUID fileId);

    Optional<Version> findOneByFileIdAndSaveDate(UUID fileId, Date saveDate);

    List<Version> findAllByFileId(UUID fileId);

    List<Version> findTop2ByFileIdAndSaveDateLessThanEqualOrderBySaveDateDesc(UUID fileId, Date saveDate);

    Optional<Version> findTopByFileIdOrderBySaveDateDesc(UUID fileId);
}
