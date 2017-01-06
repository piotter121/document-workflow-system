package pl.edu.pw.ee.pyskp.documentworkflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Version;

/**
 * Created by piotr on 06.01.17.
 */
public interface VersionRepository extends JpaRepository<Version, Long> {
}
