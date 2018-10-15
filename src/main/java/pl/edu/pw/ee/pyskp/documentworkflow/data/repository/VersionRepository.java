package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by piotr on 06.01.17.
 */
@SuppressWarnings("UnusedReturnValue")
public interface VersionRepository extends CassandraRepository<Version, MapId> {
    @Query("delete from version where file_id = :file_id")
    void deleteAllByFileId(@Param("file_id") Long fileId);

    Optional<Version> findOneByFileIdAndSaveDate(Long fileId, Date saveDate);

    List<Version> findAllByFileId(Long fileId);

    @Query("select * from version where file_id = :file_id and save_date <= :save_date order by save_date desc limit 2")
    List<Version> findTop2ByFileIdAndSaveDateLessThanEqualOrderBySaveDateDesc(@Param("file_id") Long fileId,
                                                                              @Param("save_date") Date saveDate);

    @Query("select * from version where file_id = :file_id order by save_date desc limit 1")
    Optional<Version> findTopByFileIdOrderBySaveDateDesc(@Param("file_id") Long fileId);

    Stream<Version> findByFileId(long fileId);
}
