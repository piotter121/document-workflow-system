package pl.edu.pw.ee.pyskp.documentworkflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;

/**
 * Created by piotr on 13.12.16.
 */
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
