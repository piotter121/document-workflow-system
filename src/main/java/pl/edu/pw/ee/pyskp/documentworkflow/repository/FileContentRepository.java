package pl.edu.pw.ee.pyskp.documentworkflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.FileContent;

/**
 * Created by piotr on 06.01.17.
 */
public interface FileContentRepository extends JpaRepository<FileContent, Long> {
}
