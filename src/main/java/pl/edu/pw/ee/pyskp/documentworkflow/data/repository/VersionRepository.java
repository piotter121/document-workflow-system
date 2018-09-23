package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by piotr on 06.01.17.
 */
public interface VersionRepository extends CassandraRepository<Version> {
    @Query("delete from version where file_id in (:files)")
    void deleteAllByFileIdIn(@Param("files") Collection<Long> filesIds);

    @Query("delete from version where file_id = :file_id")
    void deleteAllByFileId(@Param("file_id") Long fileId);

    Optional<Version> findOneByFileIdAndSaveDate(Long fileId, OffsetDateTime saveDate);

    List<Version> findAllByFileId(Long fileId);

    @Query("select * from version where file_id = :file_id and save_date <= :save_date order by save_date desc limit 2")
    List<Version> findTop2ByFileIdAndSaveDateLessThanEqualOrderBySaveDateDesc(@Param("file_id") Long fileId,
                                                                              @Param("save_date") OffsetDateTime saveDate);

    @Query("select * from version where file_id = :file_id order by save_date desc limit 1")
    Optional<Version> findTopByFileIdOrderBySaveDateDesc(@Param("file_id") Long fileId);

    int countDistinctByFileId(Long fileId);

    boolean existsByFileIdAndVersionString(Long fileId, String versionString);
}
