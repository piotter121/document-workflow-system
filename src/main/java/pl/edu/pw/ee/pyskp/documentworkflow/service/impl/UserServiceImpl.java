package pl.edu.pw.ee.pyskp.documentworkflow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateUserForm;
import pl.edu.pw.ee.pyskp.documentworkflow.repository.UserRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

import java.util.List;
import java.util.Optional;

/**
 * Created by piotr on 14.12.16.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUserFromForm(CreateUserForm form) {
        User user = new User();
        user.setLogin(form.getLogin());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRole(form.getRole());
        return userRepository.save(user);
    }
}
