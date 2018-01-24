package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.*;

/**
 * Created by piotr on 06.01.17.
 */
public interface VersionRepository extends CassandraRepository<Version> {
    void deleteAllByFileIdIn(Collection<UUID> filesIds);

    void deleteAllByFileId(UUID fileId);

    Optional<Version> findOneByFileIdAndSaveDate(UUID fileId, Date saveDate);

    List<Version> findAllByFileId(UUID fileId);

    List<Version> findTop2ByFileIdAndSaveDateLessThanEqualOrderBySaveDateDesc(UUID fileId, Date saveDate);

    Optional<Version> findTopByFileIdOrderBySaveDateDesc(UUID fileId);
}
