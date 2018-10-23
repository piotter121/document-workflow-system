package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by piotr on 06.01.17.
 */
public interface VersionRepository extends MongoRepository<Version, ObjectId> {
    void deleteByFile(FileMetadata file);

    List<Version> findByFile(FileMetadata file);

    Integer countByFile(FileMetadata file);

    List<Version> findByFile_IdAndSaveDateLessThanEqualOrderBySaveDateDesc(ObjectId fileId, Date saveDate);

    void deleteByFileIn(List<FileMetadata> files);

    Optional<Version> findOneByFile_IdAndSaveDate(ObjectId fileId, Date saveDate);

    Optional<Version> findTopByFileOrderBySaveDateDesc(FileMetadata fileMetadata);

    boolean existsByFile_IdAndVersionString(ObjectId fileId, String versionString);

    @SuppressWarnings("SpringDataRepositoryMethodParametersInspection")
    Stream<Version> findByFileInOrderByScoreDesc(Collection<FileMetadata> files, TextCriteria criteria);
}
