package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.UserRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.NewUserDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

/**
 * Created by piotr on 14.12.16.
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(final String email) throws UserNotFoundException {
        return userRepository.findOneByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        String currentUserEmail = getCurrentUserEmail();
        return userRepository.findOneByEmail(currentUserEmail)
                .orElse(null);
    }

    @Override
    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    @Override
    @Transactional
    public void createUserFromForm(NewUserDTO form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkIfUserExists(String email) {
        return userRepository.findOneByEmail(email).isPresent();
    }
}
