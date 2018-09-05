package pl.edu.pw.ee.pyskp.documentworkflow.data.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by piotr on 06.01.17.
 */
public interface VersionRepository extends MongoRepository<Version, ObjectId> {
    void deleteByFile_Task_Id(ObjectId taskId);

    void deleteByFile(FileMetadata file);

    List<Version> findByFile(FileMetadata file);

    Integer countByFile(FileMetadata file);

    List<Version> findByFile_IdAndSaveDateLessThanEqualOrderBySaveDateDesc(ObjectId fileId, Date saveDate);

    void deleteByFileIn(List<FileMetadata> files);

    Optional<Version> findOneByFile_IdAndSaveDate(ObjectId fileId, Date saveDate);

    Optional<Version> findTopByFileOrderBySaveDateDesc(FileMetadata fileMetadata);

    Collection<Version> findByFile_Id(ObjectId fileId);
}
